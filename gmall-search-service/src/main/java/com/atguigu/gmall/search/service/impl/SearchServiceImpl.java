package com.atguigu.gmall.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.PmsSearchParam;
import com.atguigu.gmall.bean.PmsSearchSkuInfo;
import com.atguigu.gmall.bean.PmsSkuAttrValue;
import com.atguigu.gmall.service.SearchService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService{

    @Autowired
    private JestClient jestClient;

    @Override
    public List<PmsSearchSkuInfo> list(PmsSearchParam pmsSearchParam) {
        List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
        try {
            String dsl = getDsl(pmsSearchParam);
            Search search = new Search.Builder(dsl).addIndex("gmallskuinfo")
                    .addType("PmsSkuInfo").build();
            SearchResult searchResult = jestClient.execute(search);
            List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
            for(SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits){
                PmsSearchSkuInfo source = hit.source;
                Map<String, List<String>> highlight = hit.highlight;
                if(highlight != null){
                    String skuName = highlight.get("skuName").get(0);
                    source.setSkuName(skuName);
                }
                pmsSearchSkuInfos.add(source);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pmsSearchSkuInfos;
    }

    private String getDsl(PmsSearchParam pmsSearchParam){
        long catalog3Id = pmsSearchParam.getCatalog3Id();
        String keyword = pmsSearchParam.getKeyword();
        List<PmsSkuAttrValue> skuAttrValueList = pmsSearchParam.getSkuAttrValueList();
        //jest的dsl工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //filter
        if(catalog3Id > 0){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("catalog3Id", catalog3Id);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if(skuAttrValueList != null){
            for(PmsSkuAttrValue pmsSkuAttrValue : skuAttrValueList){
                TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId",
                        pmsSkuAttrValue.getValueId());
                boolQueryBuilder.filter(termQueryBuilder);
            }
        }
        //must
        if(StringUtils.isNoneBlank(keyword)){
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
        }
        //query
        searchSourceBuilder.query(boolQueryBuilder);
        //highlight
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<span style='color:red;'>");
        highlightBuilder.field("skuName");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlight(highlightBuilder);
        //sort
        searchSourceBuilder.sort("id", SortOrder.DESC);
        //from
        searchSourceBuilder.from(0);
        //size
        searchSourceBuilder.size(20);
        return searchSourceBuilder.toString();
    }
}

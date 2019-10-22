package com.atguigu.gmall.search;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.PmsSearchSkuInfo;
import com.atguigu.gmall.bean.PmsSkuInfo;
import com.atguigu.gmall.service.SkuService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.beanutils.BeanUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GmallSearchServiceApplicationTests {

	@Reference
	private SkuService skuService;
	@Autowired
	private JestClient jestClient;

	@Test
	public void contextLoads() throws Exception {
		//put();
		get();
	}

	public void put() throws Exception {
		//mysql数据结构
		List<PmsSkuInfo> pmsSkuInfos = skuService.getAllSku();
		//es数据结构
		List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
		for(PmsSkuInfo pmsSkuInfo : pmsSkuInfos){
			PmsSearchSkuInfo pmsSearchSkuInfo = new PmsSearchSkuInfo();
			BeanUtils.copyProperties(pmsSearchSkuInfo, pmsSkuInfo);
			pmsSearchSkuInfos.add(pmsSearchSkuInfo);
		}
		for(PmsSearchSkuInfo pmsSearchSkuInfo : pmsSearchSkuInfos){
			Index put = new Index.Builder(pmsSearchSkuInfo)
					.index("gmallskuinfo").type("PmsSkuInfo").id(pmsSearchSkuInfo.getId()+"").build();
			jestClient.execute(put);
		}
	}

	public void get() throws Exception {
		//jest的dsl工具
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		//bool
		BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
		//filter
		TermQueryBuilder termQueryBuilder = new TermQueryBuilder("skuAttrValueList.valueId", "51");
		boolQueryBuilder.filter(termQueryBuilder);
		//must
		MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("skuName","华为");
		boolQueryBuilder.must(matchQueryBuilder);
		//query
		searchSourceBuilder.query(boolQueryBuilder);
		//from
		searchSourceBuilder.from(0);
		//size
		searchSourceBuilder.size(20);
		//highlight
		searchSourceBuilder.highlight(null);

		String dsl = searchSourceBuilder.toString();
		System.err.println(dsl);

		List<PmsSearchSkuInfo> pmsSearchSkuInfos = new ArrayList<>();
		Search search = new Search.Builder(dsl).addIndex("gmallskuinfo").addType("PmsSkuInfo").build();
		SearchResult searchResult = jestClient.execute(search);
		List<SearchResult.Hit<PmsSearchSkuInfo, Void>> hits = searchResult.getHits(PmsSearchSkuInfo.class);
		for(SearchResult.Hit<PmsSearchSkuInfo, Void> hit : hits){
			PmsSearchSkuInfo pmsSearchSkuInfo = hit.source;
			pmsSearchSkuInfos.add(pmsSearchSkuInfo);
		}
		System.out.println(pmsSearchSkuInfos.size());
	}
}

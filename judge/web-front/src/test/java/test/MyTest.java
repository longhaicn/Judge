package test;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import com.alibaba.fastjson.JSONObject;
import com.judge.controller.AffairController;
import com.judge.utils.DesECBUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)  //此处调用Spring单元测试类  
@WebAppConfiguration    //调用javaWEB的组件，比如自动注入ServletContext Bean等等  
@ContextConfiguration(locations = {"classpath:mybatis.xml",
		"classpath:spring-mybatis.xml","classpath:spring-mvc.xml"})//加载Spring配置文件
public class MyTest {
	@Autowired
    AffairController affairController;
	MockMvc mockMvc;
	
	@Before  
	public void setup(){  
	    mockMvc = MockMvcBuilders.standaloneSetup(affairController)
	    		.build();
	}
	@Test
    public void getArticleListTest(){
		JSONObject param = new JSONObject();
		int[] uids = {2637,2631,2634};
		param.put("aProjectId","26");
		param.put("aSponserId",2637);
		param.put("aAffairs","发起评价会议");
		param.put("aEnd","2018-03-30");
		param.put("uIds",uids);
		try{
            ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/newAffair")
                    .param("aProjectId","26")
                    .param("aSponserId","2637")
                    .param("aAffairs","发起评价会议")
                    .param("aEnd","2018-03-30 18:28:33")
                    .param("uIds","2637")
                    .param("uIds","2631")
                    .param("uIds","2634"));

            MvcResult mvcResult = resultActions.andReturn();
            String result = mvcResult.getResponse().getContentAsString();
            System.out.println("==========返回数据:" + result);
        }catch (Exception e){
		    e.printStackTrace();
        }
	}
	@Test
    public void test() throws Exception {
        String b64_ecb_str = DesECBUtil.DeReplaceChars("RtDr2KqH2MWmYo-HQ6Wc65YSycM4G7sDu9a6EBVwt4I_");
        b64_ecb_str = DesECBUtil.decrypt(b64_ecb_str, "poly_2018");
        System.out.println(b64_ecb_str);
    }
}

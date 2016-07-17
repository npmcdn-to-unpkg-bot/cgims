package com.mimi.sxp.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;
import com.chenlb.mmseg4j.analysis.MaxWordAnalyzer;
import com.chenlb.mmseg4j.analysis.SimpleAnalyzer;

public class LuceneUtil {

	 
	 public static List<String> analyzerCnStr(String str){   
		    List<String> result = new ArrayList();      
//		    Analyzer analyzer = new StandardAnalyzer();
//		    Analyzer analyzer = new SimpleAnalyzer();
//		    Analyzer analyzer = new MaxWordAnalyzer();
		    Analyzer analyzer = new ComplexAnalyzer();
		    
		    try { 
		        TokenStream tokenStream = analyzer.tokenStream("field", str);    
		        CharTermAttribute term=tokenStream.addAttribute(CharTermAttribute.class);    
		        tokenStream.reset();       
		        while( tokenStream.incrementToken() ){        
		            result.add( term.toString() );       
		        }       
		        tokenStream.end();       
		        tokenStream.close();   
		    } catch (IOException e1) {    
		        e1.printStackTrace();   
		    }   
		    return result;  
		}
	 public static void main(String args[]){
//		    List<String> l =analyzerCnStr("对于Lucene4.3开发首先摆在我们面前的第一个必须要解决的问题，就是关于中文分词的问题，因为Lucene毕竟是国外的大牛们开发的，显然会比较侧重英文文章，不过还好，在Lucene的下载包里同步了SmartCN的分词器针对中文发行的，每一次Lucene有新的版本发行，这个包同时更新。");   
//		    System.out.println(l);  
//		    String str = "读者信箱:读者X先生的单位是一家刚刚进行整体改制的公司,房产证还是改制前单位名称,现在需要房产证名称变更,请问需要缴纳契税吗?办理契税事项要什么资料?税务小博士:根据财税[2015]37号《财政部国家税务总局关于进一步支持企业事业单位改制重组有关契税政策的通知》文件规定,企业按照《中华人民共和国公司法》有关规定整体改制,包括非公司制企业改制为有限责任公司或股份有限公司,有限责任公司变更为股份有限公司,股份有限公司变更为有限责任公司,原企业投资主体存续并在改制(变更)后公司中所持股权(股份)比例超过75%,且改制(变更)后公司承继原企业权利、义务的,对改制(变更)后公司承受原企业土地、房屋权属,免征契税。符合减免税条件的纳税人应持相关资料,到房地产所在地所属税务机关办理契税减免税业务。所需提供的基本资料如下(原件及复印件):《营业执照》副本;《税务登记证》副本;法定代表人身份证明;授权委托书原件、经办人身份证明原件;《土地增值税涉税证明》;针对企业改制重组适用具体政策的有关证明资料;土地、房屋权属证书;根据企业实际情况要求提供的其他资料。纳税人可向房地产所在地所属税务机关咨询具体办理手续及流程。京城税务通依法纳税是每一位公民和企业的责任与义务。税收则是国家为满足社会公共需要,凭借公共权力,按照法律所规定的标准和程序,参与国民收入分配,强制地、无偿地取得财政收入的一种方式。从近期开始,我们特别开辟京城税务通栏目,对于一些工作生活中常见的税务知识问题给予解答和提醒。";
		    String str = "改制企业房产证变更是否需要缴纳契税";
		    System.out.println(analyzerCnStr(str));
	 }
}

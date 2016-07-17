package com.mimi.sxp.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mimi.sxp.dao.IBaseDao;
import com.mimi.sxp.dao.hibernateImpl.InformationDao;
import com.mimi.sxp.model.InformationModel;
import com.mimi.sxp.model.query.InformationQueryModel;
import com.mimi.sxp.service.IInformationService;

@Service
public class InformationService extends
		BaseService<InformationModel, String, InformationQueryModel> implements IInformationService {

	private static final Logger LOG = LoggerFactory
			.getLogger(InformationService.class);

	private InformationDao informationDao;
	
	@Resource
	@Override
	public void setBaseDao(IBaseDao<InformationModel, String, InformationQueryModel> informationDao) {
		this.baseDao = informationDao;
		this.informationDao = (InformationDao) informationDao;
	}

	@Override
	protected void initAction() {
		
		initTest();
	}
	
	private void initTest(){
			for(int i=0;i<50;i++){
				InformationModel information = new InformationModel();
				information.setName("资讯"+i);
				information.setPriority(RandomUtils.nextInt(100));
				information.setPreViewUrl("http://img.uniqueid.cn/img/LOGO.jpg");
				information.setSummary("摘要summarydflajsdfksadfl");
				information.setContent("<div class='conWord'>			<p>从政策面上来看，这或许是房贷市场最好的时代。</p>			<p>    “新政+两次降息+降准”的政策组合使得银行释放住房按揭贷款优惠的主动性大大增强，而去年同期，央行不得不借助“窗口喊话”要求银行支持合理住房需求。<iframe src='http://data.uniqueid.cn/pano/45/content' style='width:100%;height:320px' scrolling='no' frameborder='0' align=''></iframe></p>			<p>《证券日报》记者近日走访发现，就首套房而言，八八折利率目前占据主流地位，八五折也在特定银行或领域占据了一席之地。</p>			<p><strong>政策组合拳</strong></p><p><strong>护航房贷市场</strong></p>		<p>    <iframe src='http://data.uniqueid.cn/tc/136/content' style='width:100%;height:360' scrolling='no' frameborder='0' align=''></iframe></p>	<p>今年5月11日，央行宣布下调贷款基准利率0.25个百分点，五年以上商业贷款基准利率及住房公积金贷款基准利率分别降至5.65%和3.75%，从历年贷款利率水平对比来看，无论是公积金还是商业贷款，贷款利率都创出了历史新低。这也是继3月1日后，年内第二次下调贷款基准利率。加上“3·30新政”、营业税政策优惠等利好政策保驾护航，北京的房地产市场迅速回暖，房贷市场咨询和成交量迎来双重上涨，相对而言，房贷市场的优惠环境也比较宽松。</p>			<p>由于今年的“3·30新政”并未对首套房首付比例做出调整，故首套房首付比例沿用去年出台的“9·30新政”规定：对于贷款购买首套普通自住房的家庭，贷款最低首付款比例为30%。对拥有1套住房并已结清相应购房贷款的家庭，为改善居住条件再次申请贷款购买普通商品住房，银行业金融机构执行首套房贷款政策。</p>			<p>据《证券日报》记者从北京多家银行了解，央行5月份二次降息后，八八折成了首套房贷款利率的主流，并且部分折扣原为“9”字头的银行有了下行趋势，让买房人的房贷负担大幅降低，给北京楼市复苏再加助力。</p>			<p><strong>新盘最低</strong></p>			<p><strong>可达八五折优惠</strong></p>			<p>《证券日报》记者以购房者身份咨询了北京市房山区和朝阳区的几个新盘，销售人员纷纷表示：“购买首套住房，银行贷款利率最低可以给到八五折。”</p>			<p>与以往新盘项目与某一指定银行有合作关系不同的是，目前北京很多新盘都与多家不同银行有合作关系，购房者可以根据自身不同情况及喜好进行选择。银行针对特定的优质新盘首套房贷利率能给予八五折优惠，其他新盘也能做到八八折优惠。</p>			<p>以贷款100万元、20年期限为例，对比降息后基准利率（5.65%）和八八折优惠利率（4.97%）后发现，八八折利率的月供比基准利率每月少还380.87元，总利息支出比基准利率少91409.21元。</p>			<p><strong>地产中介</strong></p>			<p><strong>多有“最惠通道”</strong></p>			<p>北京市各家银行对于二手房的首套房贷款利率优惠一直也在不断调整，目前以八八折优惠为主。其中，中国银行、建设银行、交通银行、招商银行、华夏银行、中信银行、浦发银行、北京银行等银行的二手房首套房贷款利率最低均为八八折，汇丰银行最低为八六折。农业银行对于二手房首套房贷利率折扣最低，可以达到八五折。</p>			<p>虽然低折扣利率银行的数量不断增加，但是也有银行选择了“逆行”。</p>			<p>友利银行近日取消了首套房利率八五折优惠，将利率提高至8.88折。</p>			<p>从整体来看，提供九折以下利率的银行对于优惠利率的限制条件比较宽松，只要客户资质较好，基本都能申请到最优惠的利率。所谓资质较好，要求是征信记录良好，工作稳定，月收入为月还款额的2.1倍以上。除此之外，如果贷款者工作单位为500强企业则能额外加分。</p>			<p>《证券日报》记者走访了多家银行了解优惠利率的申请条件，除还款能力、征信记录良好等必备条件外，中国银行、建设银行、华夏银行、中信银行、浦发银行的个贷专员均表示无其他附加条件。而招商银行、农业银行需要客户办理银行的金卡才可申请到最低折扣；交通银行、北京银行则需要在批贷期间在银行有一定数量的存款或者购买不少于指定金额的理财产品；另外交通银行还对最低贷款金额有一定的限制，要求贷款至少120万元以上。</p>			<p>此外，虽然目前工商银行和光大银行二手房首套房贷款利率最低为九折，但工商银行的一位个贷专员对记者表示：“已经接到总行通知，6月底工行也将首套房贷款最低利率折扣降至八八折，如果客户不着急买房，又倾向在工行做贷款，可以再等一等。”光大银行的一个大堂经理也表示：“虽然目前总行的要求是最低九折，但是部分支行已经放宽了条件，央企和行政事业单位的工作人员可以申请到八八折的优惠。”</p>			<p>另外，虽然不少外资银行也是优惠多多，但由于对客户本身的资质要求很高，且对于贷款的房屋年限以及贷款金额也有诸多要求，房贷享受优惠利率的门槛过高，目前外资行并不是大多数有贷款需求客户的最优先选择。</p><p>值得注意的是，并不是所有有购房需求的个人都能申请到最低折扣。由于各家地产中介都有自己合作的银行，通过中介公司购买二手房，除特殊情况基本都能获得最低折扣。</p><p>《证券日报》的记者还发现，同一家银行与不同合作中介的折扣也有所不同。光大银行的一位个贷专员在记者表明来意后进一步解释称：“在朝阳区购买二手房，如果选择链家为中介需要去望京支行，如果选择我爱我家为中介需要去朝阳支行，不然可能只能申请到九折优惠。”</p></div>");
				information.setAuthor("作者"+i);
//				information.setContent("<html><body>资讯内容"+i+"<div style='color:red'>hahaha</div></body></html>");
				informationDao.save(information);
			}
	}
}
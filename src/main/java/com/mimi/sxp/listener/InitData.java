package com.mimi.sxp.listener;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

import com.mimi.sxp.service.IDesignImageService;
import com.mimi.sxp.service.IDesignPanoramaService;
import com.mimi.sxp.service.IDesignRingService;
import com.mimi.sxp.service.IHouseTypeService;
import com.mimi.sxp.service.IInformationService;
import com.mimi.sxp.service.IPermissionService;
import com.mimi.sxp.service.IProductService;
import com.mimi.sxp.service.IRealEstateProjectService;
import com.mimi.sxp.service.IRoleService;
import com.mimi.sxp.service.IShopService;
import com.mimi.sxp.service.IUserService;
import com.mimi.sxp.util.RSAUtil;

@Repository
public class InitData implements ApplicationListener<ContextRefreshedEvent> {
	private static final Logger LOG = LoggerFactory.getLogger(InitData.class);

	@Resource
	private IUserService userService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IPermissionService permissionService;
	@Resource
	private IRealEstateProjectService realEstateProjectService;
	@Resource
	private IHouseTypeService houseTypeService;
	@Resource
	private IDesignImageService designImageService;
	@Resource
	private IDesignRingService designRingService;
	@Resource
	private IDesignPanoramaService designPanoramaService;
	@Resource
	private IInformationService informationService;
	@Resource
	private IShopService shopService;
	@Resource
	private IProductService productService;
	
	@Value("${lucene.reload:false}")
	private boolean luceneReload;
	
	@Value("${test.init_data:false}")
	private boolean initTestData;
	
	public boolean isInitTestData() {
		return initTestData;
	}

	public void setInitTestData(boolean initTestData) {
		this.initTestData = initTestData;
	}

	public boolean isLuceneReload() {
		return luceneReload;
	}

	public void setLuceneReload(boolean luceneReload) {
		this.luceneReload = luceneReload;
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		// TODO Auto-generated method stub
		// event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")
		// System.out.println("0000-"+event.getApplicationContext().getDisplayName());
		if (event.getApplicationContext().getDisplayName()
				.equals("Root WebApplicationContext")) {
			init();
		}
		// Root WebApplicationContext
		// WebApplicationContext for namespace 'SpringMVC-servlet'
	}

	private void init() {
		initRSA();
		initData();
		if(initTestData){
			initTestData();
		}
		if(luceneReload){
			initIndex();
		}
	}
	
	private void initData(){
		permissionService.initData();
		roleService.initData();
		userService.initData();
	}
	
	private void initRSA(){
		try {
			LOG.info("开始生产密匙");
			RSAUtil.generateKeyPair();
			LOG.info("生产密匙完成");
		} catch (Exception e) {
			LOG.error("生成密钥出错！", e);
		}
	}
	
	private void initIndex(){
		informationService.initIndex();
	}
	
	private void initTestData(){

		informationService.initData();

		shopService.initData();
		productService.initData();
		
		realEstateProjectService.initData();
		houseTypeService.initData();
		designImageService.initData();
		designRingService.initData();
		designPanoramaService.initData();
		
		LOG.info("initTestData");
	}

}

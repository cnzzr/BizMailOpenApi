package cn.msdi.BizMailOpenApi.model;

import jodd.json.meta.JSON;
import cn.msdi.BizMailOpenApi.BaseModel;

/**
 * 部门实体
 * 
 * @author 张宗荣
 *
 */
public final class BizParty extends BaseModel {
	@JSON(name="srcpath")
	String srcPath;
	@JSON(name="dstpath")
	String dstPath;



	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getDstPath() {
		return dstPath;
	}

	public void setDstPath(String dstPath) {
		this.dstPath = dstPath;
	}
	
//	Field[] fields = BizParty.class.getDeclaredFields();//无法获取到父类的属性
//	@JSON(name="field",include=false)
//	Field[] _fields = ReflectUtil.getSupportedFields(getClass());
//
//	@Override
//	protected Field[] getField() {
//		return _fields;
//	}

}

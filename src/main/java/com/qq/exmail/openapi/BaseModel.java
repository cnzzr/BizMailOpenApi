package com.qq.exmail.openapi;

import java.util.LinkedHashMap;
import java.util.Map;

import jodd.json.BeanSerializer;
import jodd.json.JsonContext;
import jodd.json.JsonSerializer;
import jodd.json.meta.JSON;

public abstract class BaseModel {
	@JSON(name = "action", include = true)
	String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}


	public String toJson() {
		JsonSerializer jsonSerializer = new JsonSerializer();
		String json = jsonSerializer.serialize(this);
		return json;
	}

	/**
	 * 序列化Model为支持Post提交的字符串
	 * 
	 * @return String
	 */
	public String serialize() {
		final StringBuilder stringBuilder = new StringBuilder(256);

		JsonContext jsonContext = new JsonSerializer().createJsonContext(null);

		BeanSerializer beanSerializer = new BeanSerializer(jsonContext, this) {
			@Override
			protected void onSerializableProperty(String propertyName, Class propertyType, Object value) {
				stringBuilder.append(propertyName);
				stringBuilder.append("=");
				stringBuilder.append(value);
				stringBuilder.append("&");
			}
		};

		beanSerializer.serialize();

		int size = stringBuilder.length();
		return size > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '&' ? stringBuilder.substring(0, size - 1) : stringBuilder.toString();
	}

	/**
	 * 返回适应于jodd http提交的数据对象
	 * 
	 * @return Map
	 */
	public Map<String, Object> toPostForm() {
		final Map<String, Object> map = new LinkedHashMap<String, Object>();

		JsonContext jsonContext = new JsonSerializer().createJsonContext(null);

		BeanSerializer beanSerializer = new BeanSerializer(jsonContext, this) {
			@Override
			protected void onSerializableProperty(String propertyName, Class propertyType, Object value) {
				map.put(propertyName, value);
			}
		};

		beanSerializer.serialize();
		return map;
	}
	
	
//	protected abstract Field[] getField();
//	public String serialize() {
//		final StringBuilder stringBuilder = new StringBuilder(256);
//		String propertyName = null, jsonName = null, temp = null;
//		Object fieldValue = null;
//		try {
//			JsonAnnotationManager.TypeData typeData = JoddJson.annotationManager.lookupTypeData(this.getClass());
//			for (Field f : getField()) {
//				propertyName = f.getName();
//				propertyName = typeData.resolveJsonName(propertyName);
//				// determine if name should be included/excluded
//				boolean include = !typeData.strict;
//				// + annotations
//				include = typeData.rules.apply(propertyName, true, include);
//				// done
//
//				if (!include) {
//					continue;
//				}
//
//				if (!f.isAccessible()) {
//					f.setAccessible(true);
//				}
//				jsonName = typeData.resolveJsonName(propertyName);
//				stringBuilder.append(jsonName).append("=");
//
//				fieldValue = f.get(this);
//				if (null == fieldValue) {
//					// 跳过处理
//				} else if (fieldValue instanceof String || fieldValue instanceof Integer) {
//					stringBuilder.append(fieldValue);
//				} else if (fieldValue instanceof List) {
//					// TODO 将List和数组转换为 String
//					temp = fieldValue.toString();
//					stringBuilder.append(temp);
//				}
//				stringBuilder.append("&");
//			}
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//
//		int size = stringBuilder.length();
//		return size > 0 && stringBuilder.charAt(stringBuilder.length() - 1) == '&' ? stringBuilder.substring(0, size - 1) : stringBuilder.toString();
//	}

	
//	public Map<String, Object> toPostForm() {
//		 Map<String, Object> map = new LinkedHashMap<String, Object>();
//
//		String propertyName = null, jsonName = null, tempValue = null;
//		Object fieldValue = null;
//		try {
//			JsonAnnotationManager.TypeData typeData = JoddJson.annotationManager.lookupTypeData(this.getClass());
//			for (Field f : getField()) {
//				propertyName = f.getName();
//				// determine if name should be included/excluded
//				boolean include = !typeData.strict;
//				// + annotations
//				include = typeData.rules.apply(propertyName, true, include);
//				// done
//
//				if (!include) {
//					continue;
//				}
//
//				if (!f.isAccessible()) {
//					f.setAccessible(true);
//				}
//
//				fieldValue = f.get(this);
//				if (null == fieldValue) {
//					tempValue = "";
//				} else if (fieldValue instanceof List) {
//					// TODO 将List和数组转换为 String
//					tempValue = fieldValue.toString();
//				} else if (fieldValue instanceof Map) {
//					// TODO 将Map转换为 String
//					tempValue = fieldValue.toString();
//				} else {
//					tempValue = fieldValue.toString();
//				}
//				// 参数JsonSerializer 获取注解的方法实现序列化
//				jsonName = typeData.resolveJsonName(propertyName);
//				map.put(jsonName, tempValue);
//				// map.put(StringUtil.toCamelCase(propertyName, true, ' '), tempValue);
//			}
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		}
//		return map;
//	}

}

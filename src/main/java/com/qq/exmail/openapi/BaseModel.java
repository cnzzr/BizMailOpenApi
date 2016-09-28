package com.qq.exmail.openapi;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jodd.json.BeanSerializer;
import jodd.json.JsonContext;
import jodd.json.JsonSerializer;
import jodd.json.meta.JSON;

public abstract class BaseModel {
	/**
	 * 接口操作类型 1=DEL, 2=ADD, 3=MOD
	 */
	@JSON(name = "action", include = true)
	String action;

	public String getAction() {
		return action;
	}

	/**
	 * 动作类型（请使用OpenApiConst中常量） 1=DEL, 2=ADD, 3=MOD
	 * @see com.qq.exmail.openapi.OpenApiConst
	 * @param action
	 */
	public void setAction(String action) {
		this.action = action;
	}


	/**
	 * 对象序列化为JSON字符串
	 * @return String
	 */
	public String toJson() {
		JsonSerializer jsonSerializer = new JsonSerializer();
		String json = jsonSerializer.serialize(this);
		return json;
	}

	/**
	 * 序列化Model为支持Get提交的字符串
	 * 
	 * @return String
	 */
	public String serialize() {
		final StringBuilder stringBuilder = new StringBuilder(256);
		JsonContext jsonContext = new JsonSerializer().createJsonContext(null);
		BeanSerializer beanSerializer = new BeanSerializer(jsonContext, this) {
			@Override
			protected void onSerializableProperty(String propertyName, Class propertyType, Object value) {
				if (null == value) {
					return;
				} else if (value instanceof List) {
					@SuppressWarnings("unchecked")
					List l = (List<Object>) value;
					for (Object obj : l) {
						stringBuilder.append(propertyName).append("=").append(obj).append("&");
					}
				} else {
					stringBuilder.append(propertyName).append("=").append(value).append("&");
				}
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
//		final Map<String, Object> map = new IdentityHashMap<String, Object>();

		JsonContext jsonContext = new JsonSerializer().createJsonContext(null);
		BeanSerializer beanSerializer = new BeanSerializer(jsonContext, this) {
			@Override
			protected void onSerializableProperty(String propertyName, Class propertyType, Object value) {
				if (null == value) {
					return;
				} else if (value instanceof List) {
					//TODO 暂时跳过，对于对象中存在重复属性的接口使用Get提交数据
//					List l = (List<Object>) value;
//					for (Object obj : l) {
//						//map.put(propertyName, obj);
//						map.put(new String(propertyName), obj);//使用 IdentityHashMap 后可以添加重复Key，但是无法获取到
//					}
				} else {
					map.put(propertyName, value);
				}
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

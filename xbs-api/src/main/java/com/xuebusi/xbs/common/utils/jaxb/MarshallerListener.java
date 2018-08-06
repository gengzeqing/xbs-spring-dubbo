package com.xuebusi.xbs.common.utils.jaxb;

import java.lang.reflect.Field;
import javax.xml.bind.Marshaller.Listener;

public class MarshallerListener extends Listener {
	public static final String BLANK_CHAR = "";

	@Override
	public void beforeMarshal(Object source) {
		super.beforeMarshal(source);
		Field[] fields = source.getClass().getDeclaredFields();
		Field[] arr$ = fields;
		int len$ = fields.length;

		for (int i$ = 0; i$ < len$; ++i$) {
			Field f = arr$[i$];
			f.setAccessible(true);

			try {
				if (f.getType() == String.class && f.get(source) == null) {
					f.set(source, "");
				}
			} catch (IllegalAccessException arg7) {
				arg7.printStackTrace();
			}
		}

	}
}
package com.judge.com.judge.ldriver;

import com.google.common.base.CaseFormat;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleInsertLangDriver extends XMLLanguageDriver implements LanguageDriver {

    private final Pattern inPattern = Pattern.compile("\\(#\\{(\\w+)\\}\\)");

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        StringBuilder id_part1_sb = new StringBuilder();
        String id_part1 = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, parameterType.getSimpleName());
        for(String s : id_part1.split("_")){
            id_part1_sb.append(s.charAt(0));
        }
        Matcher matcher = inPattern.matcher(script);
        if (matcher.find()) {
            StringBuilder sb = new StringBuilder();
            StringBuilder tmp = new StringBuilder();
            sb.append("(");

            for (Field field : parameterType.getDeclaredFields()) {
                String key = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());
                if(!(id_part1_sb.toString()+"_id").equals(key) && !field.isAnnotationPresent(NonInsertDb.class)){
                    sb.append(key + ",");
                    tmp.append("#{" + field.getName() + "},");
                }
            }

            sb.deleteCharAt(sb.lastIndexOf(","));
            tmp.deleteCharAt(tmp.lastIndexOf(","));
            sb.append(") values (" + tmp.toString() + ")");

            script = matcher.replaceAll(sb.toString());
            script = "<script>" + script + "</script>";
            System.out.println(script);
        }

        return super.createSqlSource(configuration, script, parameterType);
    }
}
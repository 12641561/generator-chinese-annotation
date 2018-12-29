/**
 * Copyright 2006-2016 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3.javamapper.elements.sqlprovider;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class ProviderApplyWhereMethodGenerator extends
        AbstractJavaProviderMethodGenerator {

    private static final String[] BEGINNING_METHOD_LINES = {
            "if (example == null) {",
            "return;",
            "}",
            "",
            "String parmPhrase1;",
            "String parmPhrase1_th;",
            "String parmPhrase2;",
            "String parmPhrase2_th;",
            "String parmPhrase3;",
            "String parmPhrase3_th;",
            "if (includeExamplePhrase) {",
            "parmPhrase1 = \"%s #{example.oredCriteria[%d].allCriteria[%d].value}\";",
            "parmPhrase1_th = \"%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}\";",
            "parmPhrase2 = \"%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}\";",
            "parmPhrase2_th = \"%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}\";",
            "parmPhrase3 = \"#{example.oredCriteria[%d].allCriteria[%d].value[%d]}\";",
            "parmPhrase3_th = \"#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}\";",
            "} else {",
            "parmPhrase1 = \"%s #{oredCriteria[%d].allCriteria[%d].value}\";",
            "parmPhrase1_th = \"%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}\";",
            "parmPhrase2 = \"%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}\";",
            "parmPhrase2_th = \"%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}\";",
            "parmPhrase3 = \"#{oredCriteria[%d].allCriteria[%d].value[%d]}\";",
            "parmPhrase3_th = \"#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}\";",
            "}",
            "",
            "StringBuilder sb = new StringBuilder();",
            "List<Criteria> oredCriteria = example.getOredCriteria();",
            "boolean firstCriteria = true;",
            "for (int i = 0; i < oredCriteria.size(); i++) {",
            "Criteria criteria = oredCriteria.get(i);",
            "if (criteria.isValid()) {",
            "if (firstCriteria) {",
            "firstCriteria = false;",
            "} else {",
            "sb.append(\" or \");",
            "}",
            "",
            "sb.append('(');",
            "List<Criterion> criterions = criteria.getAllCriteria();",
            "boolean firstCriterion = true;",
            "for (int j = 0; j < criterions.size(); j++) {",
            "Criterion criterion = criterions.get(j);",
            "if (firstCriterion) {",
            "firstCriterion = false;",
            "} else {",
            "sb.append(\" and \");",
            "}",
            "",
            "if (criterion.isNoValue()) {",
            "sb.append(criterion.getCondition());",
            "} else if (criterion.isSingleValue()) {",
            "if (criterion.getTypeHandler() == null) {",
            "sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));",
            "} else {",
            "sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));",
            "}",
            "} else if (criterion.isBetweenValue()) {",
            "if (criterion.getTypeHandler() == null) {",
            "sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));",
            "} else {",
            "sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));",
            "}",
            "} else if (criterion.isListValue()) {",
            "sb.append(criterion.getCondition());",
            "sb.append(\" (\");",
            "List<?> listItems = (List<?>) criterion.getValue();",
            "boolean comma = false;",
            "for (int k = 0; k < listItems.size(); k++) {",
            "if (comma) {",
            "sb.append(\", \");",
            "} else {",
            "comma = true;",
            "}",
            "if (criterion.getTypeHandler() == null) {",
            "sb.append(String.format(parmPhrase3, i, j, k));",
            "} else {",
            "sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));",
            "}",
            "}",
            "sb.append(')');",
            "}",
            "}",
            "sb.append(')');",
            "}",
            "}",
            ""
    };

    private static final String[] LEGACY_ENDING_METHOD_LINES = {
            "if (sb.length() > 0) {",
            "WHERE(sb.toString());",
            "}"
    };

    private static final String[] ENDING_METHOD_LINES = {
            "if (sb.length() > 0) {",
            "sql.WHERE(sb.toString());",
            "}"
    };

    public ProviderApplyWhereMethodGenerator(boolean useLegacyBuilder) {
        super(useLegacyBuilder);
    }

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet<String>();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();

        if (useLegacyBuilder) {
            staticImports.add("org.apache.ibatis.jdbc.SqlBuilder.WHERE");
        } else {
            importedTypes.add(NEW_BUILDER_IMPORT);
        }

        importedTypes.add(new FullyQualifiedJavaType(
                "java.util.List"));

        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedTable.getExampleType());
        importedTypes.add(fqjt);
        importedTypes.add(new FullyQualifiedJavaType(
                String.format("%s.Criteria", fqjt.getFullyQualifiedName())));
        importedTypes.add(new FullyQualifiedJavaType(
                String.format("%s.Criterion", fqjt.getFullyQualifiedName())));

        Method method = new Method("applyWhere");
        method.setVisibility(JavaVisibility.PROTECTED);
        if (!useLegacyBuilder) {
            method.addParameter(new Parameter(NEW_BUILDER_IMPORT, "sql"));
        }
        method.addParameter(new Parameter(fqjt, "example"));
        method.addParameter(new Parameter(FullyQualifiedJavaType.getBooleanPrimitiveInstance(), "includeExamplePhrase"));

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        for (String methodLine : BEGINNING_METHOD_LINES) {
            method.addBodyLine(methodLine);
        }

        if (useLegacyBuilder) {
            for (String methodLine : LEGACY_ENDING_METHOD_LINES) {
                method.addBodyLine(methodLine);
            }
        } else {
            for (String methodLine : ENDING_METHOD_LINES) {
                method.addBodyLine(methodLine);
            }
        }

        if (context.getPlugins().providerApplyWhereMethodGenerated(method, topLevelClass,
                introspectedTable)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }
}

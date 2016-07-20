Ext.ns("webframework.${alias}");

Ext.QuickTips.init();
Ext.onReady(function() {
			// 表格
			webframework.${alias}.grid = new common.comp.AutoGrid({
						// width : defaultGridWidth,
						height : defaultGridHeight,
						layout : 'fit',
						options : {
							domain : '${alias}',
							url : '${alias}Action!list.ajax',
							deleteUrl : '${alias}Action!delete.ajax',
							baseParams : null,
							idField : [<#list primaryKey as key>"${key}"<#if (key_index != (primaryKey?size-1))>, </#if></#list>],
							fields : [<#list fields as field>
										<#assign showName = field.fieldName />
										<#if (field.fieldDisplay ??)>
										<#assign showName = field.fieldDisplay />
										</#if>
										<#assign isKey = false />
										<#list primaryKey as key>
										<#if (key == field.fieldName)>
										<#assign isKey = true />
										</#if>
										</#list>
										<#if (!(isKey))>
										{
											fieldLabel : '${showName!''}',
											name : '${field.fieldName}',
											width : 180
										}<#if (field_index != (fields?size-1))>, </#if>
										</#if>
									</#list>]
						}
					});
					
			// 表单
			webframework.${alias}.form = new common.comp.AutoForm({
						layout : 'fit',
						height : defaultFormHeight,
						options : {
							domain : '${alias}',
							submitUrl : '${alias}Action!create.ajax',
							idField : [<#list primaryKey as key>"${key}"<#if (key_index != (primaryKey?size-1))>, </#if></#list>],
							fields : [<#list fields as field>
										<#assign showName = field.fieldName />
										<#if (field.fieldDisplay ??)>
										<#assign showName = field.fieldDisplay />
										</#if>
										<#assign isForeign = false />
										<#list relations as relation>
										<#if (relation.foreignCol == field.fieldName)>
										<#assign isForeign = true />
										{
											fieldLabel : '${showName!''}',
											name : '${field.fieldName}',
											submitName : '${relation.alias}.${relation.primaryCol}'
										}
										</#if>
										</#list>
										<#list primaryKey as key>
										<#if ((key != field.fieldName) && (!(isForeign)))>
										{
											fieldLabel : '${showName!''}',// <font color="red">*</font>
											<#if (field.dbType == "DATE")>
											xtype : 'datefield',
											value : new Date(),
								            format : 'Y-m-d',
								            editable : false, 
											<#elseif (field.dbType == "NUMBER")>
											xtype : 'numberfield',
											maxLength : 9,
											</#if>
											name : '${field.fieldName}'
										}
										<#elseif (key == field.fieldName)>
										{}
										</#if>
										</#list>
										<#if (field_index != (fields?size-1))>, </#if>
									</#list>]
						}
					});
					
			var fitPanel = new Ext.Panel({
						title : '',
						renderTo : document.body,
						items : [{
									layout : 'column',
									padding : '0 0 0 0',
									border : false,
									items : [{
												columnWidth : .5,
												border : false,
												items : [webframework.${alias}.grid]
											}, {
												columnWidth : .5,
												border : false,
												items : [webframework.${alias}.form]
											}]
								}]
					});

			webframework.${alias}.grid.on('gridrowclick', function(rowdata) {// 监听表格行单击，填充表单数据
						common.extjs.utils.fillForm(webframework.${alias}.form,
								rowdata);
					});

			webframework.${alias}.form.on('submitdata', function(formdata) {// 监听表单提交，刷新表格数据
						webframework.${alias}.grid.getGridStore().reload();
					});

		});

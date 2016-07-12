/*
 * Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	config.font_names = '宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'
			+ config.font_names;
	config.fontSize_sizes = '小六/6.5pt;六号/7.5pt;小五/9pt;五号/10.5pt;小四/12pt;四号/14pt;小三/15pt;三号/16pt;小二/18pt;二号/22pt;小一/24pt;一号/26pt;小初/36pt;初号/42pt;'
			+ config.fontSize_sizes;
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
};
var config = new Object()
config.toolbar_Full = [{
	name : 'clipboard',
	items : ['Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo']// 'Source', 
}, {
	name : 'editing',
	items : ['Find', 'Replace', '-', 'SelectAll', '-', 'SpellChecker', 'Scayt']
}, {
	name : 'forms',
	items : ['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select',
			'Button', 'ImageButton', 'HiddenField']
}, {
	name : 'basicstyles',
	items : ['Bold', 'Italic', 'Underline', 'Strike', 'Subscript',
			'Superscript', '-', 'RemoveFormat']
}, {
	name : 'paragraph',
	items : ['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-',
			'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter',
			'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl']
}, {
	name : 'links',
	items : ['Link', 'Unlink', 'Anchor']
}, {
	name : 'styles',
	items : ['Styles', 'Format', 'Font', 'FontSize']
}, {
	name : 'colors',
	items : ['TextColor', 'BGColor']
}, {
	name : 'tools',
	items : ['Maximize', 'ShowBlocks']
}];

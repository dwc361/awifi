package com.awifi.bigscreen.word_fileinfo.action;

import com.awifi.bigscreen.word_fileinfo.api.service.api.IWordFileinfoService;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfo;
import org.roof.roof.dataaccess.api.Page;
import org.roof.roof.dataaccess.api.PageUtils;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("word_fileinfoAction")
public class WordFileinfoAction {
	private IWordFileinfoService wordFileinfoService;
	private IDictionaryService dictionaryService;

	// 加载页面的通用数据
	private void loadCommon(Model model){
		List<Dictionary> dicList =  dictionaryService.findByType("TEST");
		model.addAttribute("dicList", dicList);
	}

	@RequestMapping("/index")
	public String index() {
		return "/ems/firstWelcome/wordFileinfo/wordFileinfo_index.jsp";
	}

	@RequestMapping("/list")
	public String list(WordFileinfo wordFileinfo, HttpServletRequest request, Model model) {
		Page page = PageUtils.createPage(request);
		page = wordFileinfoService.page(page, wordFileinfo);
		model.addAttribute("page", page);
		model.addAllAttributes(PageUtils.createPagePar(page));
		this.loadCommon(model);
		return "/awifi/bigscreen/wordFileinfo/wordFileinfo_list.jsp";
	}
	
	
	@RequestMapping("/create_page")
	public String create_page(Model model) {
		WordFileinfo wordFileinfo = new WordFileinfo();
		model.addAttribute("wordFileinfo", wordFileinfo);
		this.loadCommon(model);
		return "/awifi/bigscreen/wordFileinfo/wordFileinfo_create.jsp";
	}
	
	@RequestMapping("/update_page")
	public String update_page(WordFileinfo wordFileinfo, Model model) {
		wordFileinfo = wordFileinfoService.load(wordFileinfo);
		model.addAttribute("wordFileinfo", wordFileinfo);
		this.loadCommon(model);
		return "/awifi/bigscreen/wordFileinfo/wordFileinfo_update.jsp";
	}

	@RequestMapping("/detail_page")
	public String detail_page(WordFileinfo wordFileinfo, Model model) {
		wordFileinfo = wordFileinfoService.load(wordFileinfo);
		model.addAttribute("wordFileinfo", wordFileinfo);
		this.loadCommon(model);
		return "/awifi/bigscreen/wordFileinfo/wordFileinfo_detail.jsp";
	}

	@RequestMapping("/create")
	public @ResponseBody Result create(WordFileinfo wordFileinfo) {
		if (wordFileinfo != null) {
			wordFileinfoService.save(wordFileinfo);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/update")
	public @ResponseBody Result update(WordFileinfo wordFileinfo) {
		if (wordFileinfo != null) {
			wordFileinfoService.updateIgnoreNull(wordFileinfo);
			return new Result("保存成功!");
		} else {
			return new Result("数据传输失败!");
		}
	}
	
	@RequestMapping("/delete")
	public @ResponseBody Result delete(WordFileinfo wordFileinfo) {
		// TODO 有些关键数据是不能物理删除的，需要改为逻辑删除
		wordFileinfoService.delete(wordFileinfo);
		return new Result("删除成功!");
	}

	@Autowired(required = true)
	public void setWordFileinfoService(
			@Qualifier("wordFileinfoService") IWordFileinfoService wordFileinfoService) {
		this.wordFileinfoService = wordFileinfoService;
	}

	@Autowired(required = true)
	public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
}

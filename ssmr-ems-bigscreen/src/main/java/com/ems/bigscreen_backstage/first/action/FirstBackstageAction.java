package com.ems.bigscreen_backstage.first.action;

import com.awifi.bigscreen.bigscreen.entity.BigscreenVo;
import com.awifi.bigscreen.bigscreen.service.api.IBigscreenService;
import com.awifi.bigscreen.chart.entity.ChartVo;
import com.awifi.bigscreen.word_fileinfo.api.service.api.IWordFileinfoService;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfo;
import com.awifi.bigscreen.word_fileinfo.entity.WordFileinfoVo;
import org.roof.spring.Result;
import org.roof.web.dictionary.entity.Dictionary;
import org.roof.web.dictionary.service.api.IDictionaryService;
import com.awifi.bigscreen.bigscreen.entity.BigscreenReTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("ems/bigscreen_backstage/FirstBackstageAction")
public class FirstBackstageAction {
    private IWordFileinfoService wordFileinfoService;
    private IDictionaryService dictionaryService;
    private IBigscreenService bigscreenService;

    // 加载页面的通用数据
    private void loadCommon(Model model){
        List<Dictionary> dicList =  dictionaryService.findByType("TEST");
        model.addAttribute("dicList", dicList);
    }
    /**
     * 一层架构配置数据加载
     * @param request
     * @param response
     * @return json
     */
    @RequestMapping("/get_bigscreen_first_data")
    public @ResponseBody
    Result get_bigscreen_first_data(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();

        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        // 加载大屏信息
        BigscreenVo bigscreenVo = new BigscreenVo();
        bigscreenVo.setEnabled("1");
        bigscreenVo.setRe_type(BigscreenReTypeEnum.First.getCode());
        List<BigscreenVo> bigscreenList = bigscreenService.selectForList(bigscreenVo);
        if(bigscreenList!=null && bigscreenList.size()>0) {
            bigscreenVo = bigscreenList.get(0);
        }
        WordFileinfo wordFileinfo = new WordFileinfo();
        wordFileinfo.setScreen_id(bigscreenVo.getId());
        List<WordFileinfoVo> wordList = wordFileinfoService.selectForList(wordFileinfo);
        List<String> path = new ArrayList<String>();
        for(WordFileinfoVo word : wordList) {
            path.add(basePath +"/ems/bigscreen_show/first/svg/");
        }
        map.put("wordList",wordList);
        map.put("wordpath",path);
        return new Result("一层架构配置数据加载成功!", map);
    }

    @Autowired(required = true)
    public void setWordFileinfoService(@Qualifier("wordFileinfoService") IWordFileinfoService wordFileinfoService) {
        this.wordFileinfoService = wordFileinfoService;
    }
    @Autowired(required = true)
    public void setDictionaryService(@Qualifier("dictionaryService") IDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
    @Autowired(required = true)
    public void setBigscreenService(@Qualifier("bigscreenService") IBigscreenService bigscreenService) {
        this.bigscreenService = bigscreenService;
    }
}

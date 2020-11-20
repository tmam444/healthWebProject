package com.healthchang.demo.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthSearchController {

    @GetMapping("/search")
    public String searchHeatlh(@RequestParam(value = "find", defaultValue = "강남구") String find, @RequestParam(value = "page", defaultValue = "1") int page, Model model){
        model.addAttribute("find", find);
        model.addAttribute("page", page);
        return "search";
    }

    @ResponseBody
    @GetMapping("/searchApi")
    public String searchHealth(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "find", defaultValue = "") String find){
        page = page != 0 ? page*15-14 : 1;
        int limitPage = page + 14;
        String urlstr = "http://openapi.seoul.go.kr:8088/747543646a746d6135335372684742/json/TbPublicSptCenter2019/" +  page + "/" + limitPage + "/" + find;

        StringBuffer stringBuffer = CommonMethod.apiURLimport(urlstr, "GET");
        String returnMsg = "";

        try {
            JSONParser jsonParse = new JSONParser();
            //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다. JSONObject jsonObj = (JSONObject) jsonParse.parse(jsonData); //JSONObject에서 PersonsArray를 get하여 JSONArray에 저장한다. JSONArray personArray = (JSONArray) jsonObj.get("Persons");
            JSONObject jObject = (JSONObject) jsonParse.parse(stringBuffer.toString());
            JSONObject jsonObject = (JSONObject) jsonParse.parse(jObject.get("TbPublicSptCenter2019").toString());
            returnMsg = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMsg;
    }

}

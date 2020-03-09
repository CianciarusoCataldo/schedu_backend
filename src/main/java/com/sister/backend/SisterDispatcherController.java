package com.sister.backend;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import com.dispatcher.Dispatcher;
import com.json.JsonUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class SisterDispatcherController {

  public static Dispatcher dispatcher = new SisterDispatcher();

  @RequestMapping("/")
  public String test() {
    return "Dispatcher for legacy system, v. 0.7.1";
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @ResponseStatus(HttpStatus.OK)
  @RequestMapping(value = "/process", method = RequestMethod.POST, consumes = "application/json")
  public String process(@RequestBody String payload) {
    try {
      ArrayList<Object> cfs = new ArrayList<>();
      JSONObject resjson=new JSONObject();
      JSONArray jcfs = new JSONArray(payload);
      int n = jcfs.length();
      int i;

      for (i = 0; i < n; ++i) {
        JSONObject person = jcfs.getJSONObject(i);
        cfs.add(person.getString("cf"));
      }
      resjson.put("cfs", dispatcher.getResult(cfs));
      return dispatcher.getResult(cfs).toString();
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
  }

  @RequestMapping(value = "/addAccount", method = RequestMethod.POST, consumes = "application/json")
  @ResponseStatus(HttpStatus.OK)
  public String addSisterAccounts(@RequestBody String payload) {
    try {
      JSONObject obj = new JSONObject(payload);
      SisterMockAccount sisacc = new SisterMockAccount(obj.getString("Username"), obj.getString("Password"));
      if (!sisacc.isOnline()) {
        return "error";
      }

      boolean res = dispatcher.addAccount(sisacc);
      return res ? "ok" : "exist";
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println(payload);
      return "error";
    }
  }

  @CrossOrigin(origins = "http://localhost:3000")
  @RequestMapping(value = "/deleteAccount", method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.OK)
  public String deleteSisterAccounts(@RequestBody String payload) {
    try {
      JSONObject obj=new JSONObject(payload);
      int id=Integer.valueOf(obj.getInt("id"));
      if(id>=dispatcher.getAccounts().size()){
        return "invalid";
      }
      boolean res = ((SisterDispatcher)dispatcher).removeAccount(id);
      return res ? "removed" : "invalid";
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println(payload);
      return "error";
    }
  }

  @RequestMapping(value = "/getAccount", method = RequestMethod.GET)
  public String getSisterAccounts() {
    JSONArray ar = JsonUtils.getJson(dispatcher.getAccounts());
    return ar.toString();
  }

}
package org.owasp.webgoat.plugin;

import org.owasp.webgoat.assignments.Endpoint;

/**
 * Created by jason on 9/30/17.
 */

public class CSRFGetFlag extends Endpoint {

    @Autowired
    UserSessionData userSessionData;
    @Autowired
    private PluginMessages pluginMessages;

    @RequestMapping(produces = {"application/json"}, method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> invoke(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, Object> response = new HashMap<>();

        String host = (req.getHeader("host") == null) ? "NULL" : req.getHeader("host");
//        String origin = (req.getHeader("origin") == null) ? "NULL" : req.getHeader("origin");
//        Integer serverPort = (req.getServerPort() < 1) ? 0 : req.getServerPort();
//        String serverName = (req.getServerName() == null) ? "NULL" : req.getServerName();
        String referer = (req.getHeader("referer") == null) ? "NULL" : req.getHeader("referer");
        String[] refererArr = referer.split("/");



        if (referer.equals("NULL")) {
            if (req.getParameter("csrf").equals("true")) {
//--VULNERABLE----
            	Random random = new Random();
                userSessionData.setValue("csrf-get-success", random.nextInt(65536));
//--VULNERABLE----
//--FIX-----------
//                SecureRandom random = new SecureRandom();
//                byte sessBytes[] = new byte[32];
//                userSessionData.setValue("csrf-get-success", random.nextBytes(sessBytes));
//--FIX-----------
                
                response.put("success", true);
                response.put("message", pluginMessages.getMessage("csrf-get-null-referer.success"));
                response.put("flag", userSessionData.getValue("csrf-get-success"));
            } else {
                Random random = new Random();
                userSessionData.setValue("csrf-get-success", random.nextInt(65536));
                response.put("success", true);
                response.put("message", pluginMessages.getMessage("csrf-get-other-referer.success"));
                response.put("flag", userSessionData.getValue("csrf-get-success"));
            }
        } else if (refererArr[2].equals(host)) {
            response.put("success", false);
            response.put("message", "Appears the request came from the original host");
            response.put("flag", null);
        } else {
            Random random = new Random();
            userSessionData.setValue("csrf-get-success", random.nextInt(65536));
            response.put("success", true);
            response.put("message", pluginMessages.getMessage("csrf-get-other-referer.success"));
            response.put("flag", userSessionData.getValue("csrf-get-success"));
        }

        return response;

    }

    @Override
    public String getPath() {
        return "/csrf/basic-get-flag";
    }
}

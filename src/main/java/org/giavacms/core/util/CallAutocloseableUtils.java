package org.giavacms.core.util;

import org.jboss.logging.Logger;

public class CallAutocloseableUtils {
    static Logger logger = Logger.getLogger(CallAutocloseableUtils.class);

    // public static Content content(String targetHost, String targetPath, String eventId,
    // String stepId) throws Exception
    // {
    // try (WebTargetClosable webTargetClosable = new WebTargetClosable(targetHost, targetPath))
    // {
    // webTargetClosable.webTarget = webTargetClosable.webTarget.resolveTemplate("eventId", eventId).resolveTemplate(
    // "idEvent", eventId);
    // if (stepId != null)
    // {
    // webTargetClosable.webTarget = webTargetClosable.webTarget.resolveTemplate("stepId", stepId)
    // .resolveTemplate("idStep", stepId);
    // }
    // webTargetClosable.response = webTargetClosable.webTarget.request().get();
    // if (webTargetClosable.response.getStatus() == Status.OK.getStatusCode())
    // {
    // Content content = webTargetClosable.response.readEntity(Content.class);
    // return content;
    // }
    // return null;
    // }
    // }
    //
    // public static boolean call(String targetHost, String targetPath, Content content) throws Exception
    // {
    // try (WebTargetClosable webTargetClosable = new WebTargetClosable(targetHost, targetPath))
    // {
    // webTargetClosable.response = webTargetClosable.webTarget
    // .request()
    // .buildPost(
    // Entity.entity(content, MediaType.APPLICATION_JSON))
    //
    // .invoke();
    // if (webTargetClosable.response != null
    // && (
    // webTargetClosable.response.getStatus() == Status.OK.getStatusCode() ||
    // webTargetClosable.response.getStatus() == Status.NO_CONTENT.getStatusCode()))
    // {
    // return true;
    // }
    // else
    // {
    // logger.error("targetHost:" + targetHost + ",targetPath: " + targetPath + "code: "
    // + webTargetClosable.response.getStatus() + " - status: "
    // + Status.fromStatusCode(webTargetClosable.response.getStatus())
    // + " - family: "
    // + Status.Family.familyOf(webTargetClosable.response.getStatus()));
    // }
    // }
    // catch (Exception e)
    // {
    // logger.error(e.getMessage(), e);
    // return false;
    // }
    // return false;
    // }
    //
    // public static boolean call(String targetHostPath, Content content) throws Exception
    // {
    // try (WebTargetClosable webTargetClosable = new WebTargetClosable(targetHostPath))
    // {
    // webTargetClosable.response = webTargetClosable.webTarget
    // .request()
    // .buildPost(
    // Entity.entity(content, MediaType.APPLICATION_JSON))
    //
    // .invoke();
    // if (webTargetClosable.response != null
    // && (
    // webTargetClosable.response.getStatus() == Status.OK.getStatusCode() ||
    // webTargetClosable.response.getStatus() == Status.NO_CONTENT.getStatusCode()))
    // {
    // return true;
    // }
    // else
    // {
    // logger.error("code: " + webTargetClosable.response.getStatus() + " - status: "
    // + Status.fromStatusCode(webTargetClosable.response.getStatus())
    // + " - family: "
    // + Status.Family.familyOf(webTargetClosable.response.getStatus()));
    // }
    // }
    // catch (Exception e)
    // {
    // logger.error(e.getMessage(), e);
    // return false;
    // }
    // return false;
    // }
    //
    // public static void logs(String targetHostPath, String eventId,
    // String stepId, String when, String serviceName, Map<String, String> logs) throws Exception
    // {
    // try (WebTargetClosable webTargetClosable = new WebTargetClosable(targetHostPath))
    // {
    // Content content = new Content();
    // content.put(AppConstants.EVENT_ID, eventId);
    // content.put(AppConstants.STEP_ID, stepId);
    // content.put(AppConstants.WHEN, when);
    //
    // for (String key : logs.keySet())
    // {
    // content.getStepValuesIn(stepId).put(key, logs.get(key));
    // }
    // content.getStepValuesIn(stepId).put(AppConstants.HOST,
    // System.getProperty(AppConstants.JBOSS_QUALIFIED_HOST_NAME_PROPERTY));
    // content.getStepValuesIn(stepId).put(AppConstants.SERVICE_NAME, serviceName);
    //
    // webTargetClosable.response = webTargetClosable.webTarget.request()
    // .buildPost(Entity.entity(content, MediaType.APPLICATION_JSON))
    // .invoke();
    // if (webTargetClosable.response.getStatus() != Status.OK.getStatusCode())
    // {
    // logger.error("log operations: " + webTargetClosable.response.getStatus() + " --> "
    // + webTargetClosable.response.getStatusInfo());
    // }
    // }
    // }
    //
    // public static void logs(String targetHost, String targetPath, String eventId,
    // String stepId, String when, String serviceName, Map<String, String> logs) throws Exception
    // {
    // try (WebTargetClosable webTargetClosable = new WebTargetClosable(targetHost, targetPath))
    // {
    // Content content = new Content();
    // content.put(AppConstants.EVENT_ID, eventId);
    // content.put(AppConstants.STEP_ID, stepId);
    // content.put(AppConstants.WHEN, when);
    //
    // for (String key : logs.keySet())
    // {
    // content.getStepValuesIn(stepId).put(key, logs.get(key));
    // }
    // content.getStepValuesIn(stepId).put(AppConstants.HOST,
    // System.getProperty(AppConstants.JBOSS_QUALIFIED_HOST_NAME_PROPERTY));
    // content.getStepValuesIn(stepId).put(AppConstants.SERVICE_NAME, serviceName);
    //
    // webTargetClosable.response = webTargetClosable.webTarget.request()
    // .buildPost(Entity.entity(content, MediaType.APPLICATION_JSON))
    // .invoke();
    // if (webTargetClosable.response.getStatus() != Status.OK.getStatusCode())
    // {
    // logger.error("log operations: " + webTargetClosable.response.getStatus() + " --> "
    // + webTargetClosable.response.getStatusInfo());
    // }
    // }
    // }

}

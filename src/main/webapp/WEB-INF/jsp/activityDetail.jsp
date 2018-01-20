<%--
  Created by IntelliJ IDEA.
  User: lizai
  Date: 15/8/20
  Time: 上午11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title>乐友</title>
  <meta charset="UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0a3/jquery.mobile-1.0a3.min.css" />
  <script type="text/javascript" src="http://code.jquery.com/jquery-1.5.min.js"></script>
  <script type="text/javascript" src="http://code.jquery.com/mobile/1.0a3/jquery.mobile-1.0a3.min.js"></script>
  <script type="text/javascript" src="share.js"></script>
  <link rel="stylesheet" href="share.css" type="text/css" />
</head>
<body>
<div data-role="page" id="home_page" data-theme="a|b|c|d|e" data-dom-cache="true">
  <div data-role="header">
    <h1 id="headerTitle">乐友</h1>
  </div>
  <div data-role="content">
    <div >
      <p id="activity_title" class="activity_title" align="center">${activity.activityTitle}</p>
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tbody>
        <td height="50" align="center"><a href="#" target="_self"><img src="${activity.activityBigimage}" width="300px" height="220px"></a><br /></td>
        </tbody>
      </table>
    </div>
    <ul data-role="listview">
      <li id="activity_basic_info" data-role="list-divider"  class="colorGray">活动信息</li>

      <li  data-icon="false"><a id="start_time" href="#">开始时间</a></li>
      <li>${activity.startTime}</li>

      <li  data-icon="false"><a id="end_time" href="#">结束时间</a></li>
      <li>${activity.endTime}</li>

      <li  data-icon="false"><a id="address" href="#">活动地点</a></li>
      <li>${activity.activityAddress}</li>

      <li  data-icon="false"><a id="enroll_count" href="#">报名人数</a></li>
      <li>${activity.joindNumber}</li>

      <li  data-icon="false"><a id="view_count" href="#">演出人员</a></li>
      <li>${activity.activityPlayer}</li>

      <li  data-icon="false"><a id="contact_phone" href="${activity.activityTicketUrl}">购票地址</a></li>

      <li id="activity_more_info" data-role="list-divider"   class="colorGray">更多描述</li>
      <li><a id="a_intro" href="#contents_page">活动详细介绍</a></li>
      <li><a id="a_path" href="#contents_page1">主办单位</a></li>
      <%--<li><a id="a_payment" href="#contents_page2">活动费用</a></li>--%>
      <%--<li><a id="a_arrange" href="#contents_page3">行程安排</a></li>--%>
      <%--<li><a id="a_equipment" href="#contents_page4">装备要求</a></li>--%>
    </ul>
  </div>
  <div data-role="footer"  data-position="fixed" data-theme="c">
    <div data-role="navbar">
      <li><a href="share.html" data-role="tab" data-icon="arrow-d">下载APP</a></li>
    </div>
  </div>
</div>
<div data-role="page" id="contents_page" data-title="活动详细介绍" data-theme="a|b|c|d|e" data-dom-cache="true">
  <div data-role="header">
    <a href="#" id="nav_back_btn" data-icon="arrow-l" class="back_btn" href="" data-role="button" data-rel="back" data-theme="x">返回</a>
    <h1 id="contents_header_title">活动详细介绍</h1>
  </div>
  <p id="details_text">${activity.activityDescription}</p>
  <div data-role="footer"  data-position="fixed" data-theme="c">
    <div data-role="navbar">
      <li><a href="share.html" data-role="tab" data-icon="arrow-d">下载APP</a></li>
    </div>
  </div>
</div>
<div data-role="page" id="contents_page1" data-title="路线描述" data-theme="a|b|c|d|e" data-dom-cache="true">
  <div data-role="header">
    <a href="#" id="nav_back_btn1" data-icon="arrow-l" class="back_btn" href="" data-role="button" data-rel="back" data-theme="x">返回</a>
    <h1 id="contents_header_title1">主办单位</h1>
  </div>
  <p id="details_text1">${activity.activitySponsor}</p>
  <div data-role="footer"  data-position="fixed" data-theme="c">
    <div data-role="navbar">
      <li><a href="share.html" data-role="tab" data-icon="arrow-d">下载APP</a></li>
    </div>
  </div>
</div>
</div>
</body>
</html>
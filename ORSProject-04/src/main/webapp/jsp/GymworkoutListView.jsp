
<%@page import="in.co.rays.proj4.bean.GymworkoutBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.BaseCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.controller.GymworkoutListCtl"%>
<%@page import="java.util.Iterator"%>

<html>
<head>
<title>Gym List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>

<body>

<%@include file="Header.jsp"%>

<jsp:useBean id="bean" class="in.co.rays.proj4.bean.GymworkoutBean" scope="request"></jsp:useBean>

<div align="center">

<h1 style="color: navy;">Gym List</h1>

<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
<h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>

<form action="<%=ORSView.GYMWORKOUT_LIST_CTL%>" method="post">

<%
int pageNo = ServletUtility.getPageNo(request);
int pageSize = ServletUtility.getPageSize(request);
int index = ((pageNo - 1) * pageSize) + 1;

int nextPageSize = request.getAttribute("nextListSize") != null
        ? DataUtility.getInt(request.getAttribute("nextListSize").toString())
        : 0;

List<GymworkoutBean> gymList =
(List<GymworkoutBean>) request.getAttribute("list");

if (gymList == null) {
    gymList = new java.util.ArrayList<GymworkoutBean>();
}

Iterator<GymworkoutBean> it = gymList.iterator();
%>

<%
if (gymList.size() != 0) {
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<table width="100%">
<tr>
<td align="center">

<label><b>Name :</b></label>
<input type="text" name="name" placeholder="Enter Name"
value="<%=ServletUtility.getParameter("name", request)%>">&emsp;

<input type="submit" name="operation"
value="<%=GymworkoutListCtl.OP_SEARCH%>">

<input type="submit" name="operation"
value="<%=GymworkoutListCtl.OP_RESET%>">

</td>
</tr>
</table>

<br>

<table border="1" width="100%" style="border: groove;">

<tr style="background-color:#e1e6f1;">
<th width="5%"><input type="checkbox" id="selectall"></th>
<th width="5%">S.No</th>
<th width="20%">Name</th>
<th width="20%">Workout</th>
<th width="20%">Trainer</th>
<th width="20%">Schedule</th>
<th width="10%">Edit</th>
</tr>

<%
while(it.hasNext()){
bean = it.next();
%>

<tr>

<td align="center">
<input type="checkbox" class="case" name="ids"
value="<%=bean.getId()%>">
</td>

<td align="center"><%=index++%></td>

<td align="center"><%=bean.getName()%></td>

<td align="center"><%=bean.getWorkout()%></td>

<td align="center"><%=bean.getTrainerName()%></td>

<td align="center"><%=bean.getSchedule()%></td>

<td align="center">
<a href="<%=ORSView.GYMWORKOUT_CTL%>?id=<%=bean.getId()%>">Edit</a>
</td>

</tr>

<%
}
%>

</table>

<br>

<table width="100%">

<tr>

<td width="25%">

<input type="submit" name="operation"
value="<%=GymworkoutListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>

</td>

<td align="center" width="25%">

<input type="submit" name="operation"
value="<%=GymworkoutListCtl.OP_NEW%>">

</td>

<td align="center" width="25%">

<input type="submit" name="operation"
value="<%=GymworkoutListCtl.OP_DELETE%>">

</td>

<td align="right" width="25%">

<input type="submit" name="operation"
value="<%=GymworkoutListCtl.OP_NEXT%>"
<%=nextPageSize > 0 ? "" : "disabled"%>>

</td>

</tr>

</table>

<%
} else {
%>

<table>
<tr>
<td align="right">
<input type="submit" name="operation"
value="<%=GymworkoutListCtl.OP_BACK%>">
</td>
</tr>
</table>

<%
}
%>

</form>

</div>

</body>
</html>
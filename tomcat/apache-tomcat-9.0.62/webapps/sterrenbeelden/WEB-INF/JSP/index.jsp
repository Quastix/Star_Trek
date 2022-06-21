<%@page contentType="text/html" pageEncoding="UTF-8" session="false"
	trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="nl">
<head>
<title>Sterrenbeelden</title>
<link rel="icon" href="images/ster.ico" type="image/x-icon">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/styles/default.css" />
</head>
<body>
	<h1>Sterrenbeeld</h1>
	<form>
		<label>Geboortedatum: <input name="datum"
			value="${param.datum}" autofocus placeholder="dag-maand-jaar" />
		</label> <input type="submit" value="Sterrenbeeld" />
		<c:if test="${not empty datumfout}">
			<div class="fout">${datumfout}</div>
		</c:if>
	</form>
	<c:if test="${not empty beeld}">
		<img
			src="${pageContext.servletContext.contextPath}/images/${beeld}.png"
			alt="${beeld}" />
		<div>
			<span class="${beeld}">Je sterrenbeeld: ${beeld}</span>
		</div>
	</c:if>
	<h1>Gastenboek</h1>
	<form method="post">
		<label>Naam: <input name="naam" value="${param.naam}" required/></label>
		<label>Bericht:<input name="bericht" value="${param.bericht}"  size="80" required/></label>
		<input type="submit" value="Toevoegen" />
		<c:if test="${not empty naamfout}">
			<div class="fout">${naamfout}</div>
		</c:if>
		<c:if test="${not empty berichtfout}">
			<div class="fout">${berichtfout}</div>
		</c:if>
	</form>
	<c:if test="${not empty gastenboek}">
	<dl>
		<c:forEach var="entry" items="${gastenboek.entries}">
			<dt>${entry.naam}</dt>
			<dd>${entry.bericht}</dd>
		</c:forEach>
		</dl>
	</c:if>
</body>
</html>

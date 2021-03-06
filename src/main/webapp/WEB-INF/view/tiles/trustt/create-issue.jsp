<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="container">
    <%--create an issue section--%>
    <H1>Create an Issue</H1>

    <c:url var="createIssue" value="/trustt/create-issue"/>
    <form:form id="issueForm" class="form-signin" method="post" commandName="issue"
               action="${createIssue}" modelAttribute="issueForm">

        <table width="600px" height="350px">
            <tr>
                <td><form:label path="title">Title</form:label></td>
                <td><form:input type="text" path="title"/></td>
            </tr>

            <tr>
                <td><form:label path="description">Description</form:label></td>
                <td><form:textarea path="description" rows="5" cols="50"/></td>
            </tr>

            <tr>
                <td><form:label path="type">Type</form:label></td>
                <td><form:select path="type" items="${types}" /></td>
            </tr>

            <tr>
                <td><form:label path="priority">Priority</form:label></td>
                <td><form:select path="priority" items="${priorities}" /></td>
            </tr>

            <tr>
                <td><form:button type="submit" value="Create">Create</form:button></td>
            </tr>
        </table>
    </form:form>
</div>
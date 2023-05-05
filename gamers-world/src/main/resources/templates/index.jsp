<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--Webpage made by Tabitha Ristoff, Justin Turziak, Joshua Lecik-->
<html xmlns:th="http://www.thymeleaf.org">
  <head>
    <title>Gamer's World</title>
    <link rel="stylesheet" th:href="@{/css/styles.css}" href="../css/styles.css"> 
    <link rel="stylesheet" th:href="@{/css/event.css}" href="../css/event.css"> 
    <style>
      .joingroup {
				padding: 10px 10px;
				width: 100px;
				height: 40px;
				float: right;
				text-align: center;
			}
    </style>
    <meta name="description" content="Welcome to Gamer's World."> 
    <script src="/js/event.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
  </head>

  <body>
    <a th:href="@{/index}"><div id="banner"></div></a>



    <!--Navigation Menu-->
    <div class="nav">
      <a th:href="@{/index}">Home</a>
      <a th:href="@{/messages}">Messages</a>
      <a th:href="@{/profile}">Profile</a>
      <form th:action="@{/logout}" method="post">
        <button type = "submit"> Logout</button>
      </form>
    </div>

    <div class = "indexcontainer">
    <div class="box-group">
      <!-- Groups -->
      <div class = "groups">
        <h1><a th:href="@{/groups}">Groups</a></h1>
        <tr th:each="group, iterStat : ${groups}" th:if="${iterStat.index} lt 3">
        <div class = "groups post">
          <h2><span th:text="${group.name}"></span></h2>
          <form th:action="@{/joingroup/{id}(id = ${group.groupID})}" method="post">
						<button type="submit" class="joingroup">Join</button>
					</form>
          <p class="tab"><span th:text="${group.description}"></span></p>
        </div>
    </div>
  </tr>
   
    <!-- Events -->
    <div class = "events">
      <h1><a th:href="@{/events}">Events</a></h1>
      <!-- show only first 5 -->
      <tr th:each="event, iterStat : ${events}" th:if="${iterStat.index} lt 3">
      <div class = "events post">
        <h2><span th:text="${event.date}"></span> | <span th:text="${event.location}"></span></h2>
        <h2><a th:href="@{/event/{eventId}(eventId=${event.eventId})}"><span th:text="${event.eventName}"></span></a></h2>
        <p class="tab"><span th:text="${event.description}"></span></p>
        <p>Game: <span th:text="${event.game}"></span></p>
        <p>Play Level: <span th:text="${event.playLevel}"></span></p>
        <button class="btn" th:if="${!#lists.contains(event.attendeeList, user)}" th:id="${event.eventId}" th:value="${event.eventId}" onclick="rsvp(this.value)">RSVP</button>
        <button class="btn" th:unless="${!#lists.contains(event.attendeeList, user)}" style="background-color: #712277">Attending</button>
        <input hidden th:value="${event.eventId}" id="id" name="id"></p>
      </div>
    </div>
  </tr>
</div>

<!--
    <div class="box-group2">
      
      
      <div class = "trends-box"> 
        <h1>Trends</h1>
        <div class = "trends post">
          <h2>Trend Name</h2>
          <p class="tab">Description</p>
        

      <div class="trends-box">
      <h1>Trends</h1>
      <div class = "trends post">
        <ul>
          <li th:each="game : ${trends}">
              <a th:href="@{/games/details/{id}(id=${game.getId()})}" th:text="${game.getName()}"></a>
          </li>
      </ul>
      </div>
    </div>
  -->

  <div class="trends">
    <h2>Trending</h2>
    <ul>
      <c:forEach items="${trends}" var="game">
        <li>${game.name}</li>
      </c:forEach>
    </ul>
  </div>


    <!-- Gamers -->
  <div class="gamers">
    <h1><a th:href="@{/gamers}">Gamers</a></h1>
    <tr th:each="gamer, iterStat : ${gamers}" th:if="${iterStat.index} lt 3">
    <div class = "gamers post">
      <h2><a href=""><span th:text="${gamer.profile.username}"></span></a></h2>
      <p class="tab"><span th:text="${gamer.profile.description}"></span></p>
      <p>Games: </p>
      <div th:each="game : ${gamer.profile.games}">
        <p th:text="${game}"></p>
      </div>
    </div>
  </div>
</tr>
</div>

  </div>

</div>

    <footer>
      <!--Copyright information-->
      <br>
      <p></p>&copy; 2023 All rights reserved</p>
    </footer>

  </body>
</html>
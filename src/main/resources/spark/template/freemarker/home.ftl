<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
    <meta http-equiv="refresh" content="10">
    <title>${title} | Web Checkers</title>
    <link rel="stylesheet" type="text/css" href="/css/style.css">
</head>
<body>
  <div class="page">
  
    <h1>Web Checkers</h1>
    
    <div class="navigation">

      <#if username??>
        <a href="/">${username}</a>
        <a href = "/signout">Sign Out</a>

      <#else>
        <a href="/">My Home</a>
        <a href="/signin" role="button">Sign In</a>
      </#if>

    </div>
    
    <div class="body">
      <#if username??>
        <p>Welcome, ${username} </p>
        <p>There are currently ${numplayers} player(s) playing web-checkers at the moment.</p>
        <#if message??>
              <div class="error">${message}</div>
        </#if>
        <ul class = "players" >
            <#list playerlobby as currplayer>
              <li><a href = "/game?opponent=${currplayer}">${currplayer}</a></li>
            </#list>
        </ul>

      <#else>
        <p>Welcome to the world of online Checkers. Sign in to play a game.</p>
        <p>There are currently ${numplayers} player(s) playing web-checkers at the moment.</p>
      </#if>

    </div>

  </div>
</body>
</html>

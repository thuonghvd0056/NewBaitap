package endpoint;

import com.google.gson.Gson;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import entity.Data;
import entity.Game;
import util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class GameApi extends HttpServlet {
    static{
        ObjectifyService.register(Game.class);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        Game game = new Gson().fromJson(content, Game.class);

        Key<Game> gameKey = ofy().save().entity(game).now();
        String status = "201";
        String message = "OK_CREATED";
        resp.setStatus(HttpServletResponse.SC_CREATED);
        Data data = new Data();
        data.setStatus(status);
        data.setMessage(message);
        data.setData(game);
        resp.getWriter().print(new Gson().toJson(data));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().println(new Gson().toJson(ofy().load().type(Game.class).list()));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        Game updateGame = new Gson().fromJson(content, Game.class);
        updateGame.getId();
        Game existGame = ofy().load().type(Game.class).id(updateGame.getId()).now();
        if (existGame == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy game.");
            return;
        }
        existGame.setName(updateGame.getName());
        existGame.setImage(updateGame.getImage());
        existGame.setPrice(updateGame.getPrice());
        ofy().save().entity(existGame).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(new Gson().toJson(existGame));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        String content = StringUtil.convertInputStreamToString(req.getInputStream());
        Game updateGame = new Gson().fromJson(content, Game.class);
        long id = updateGame.getId();
        Game existGame = ofy().load().type(Game.class).id(id).now();
        if (existGame == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Không tìm thấy game.");
            return;
        }
        existGame.setStatus(-1);
        Key<Game> key = ofy().save().entity(existGame).now();
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().print(new Gson().toJson(key));
    }
}

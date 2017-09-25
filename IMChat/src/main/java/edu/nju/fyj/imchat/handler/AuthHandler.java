package edu.nju.fyj.imchat.handler;

import edu.nju.fyj.imchat.connection.ClientSession;
import edu.nju.fyj.imchat.service.auth.AuthService;
import edu.nju.fyj.imchat.connection.SessionManager;
import edu.nju.fyj.imchat.entity.Body;
import edu.nju.fyj.imchat.entity.Login;
import edu.nju.fyj.imchat.entity.Packet;
import edu.nju.fyj.imchat.protocol.Header;
import edu.nju.fyj.imchat.protocol.PacketType;
import edu.nju.fyj.imchat.service.response.ResponseService;
import edu.nju.fyj.imchat.service.response.impl.ResponseServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by fangyj on 2017/9/24.
 */
public class AuthHandler extends SimpleChannelInboundHandler<Packet> {

    private SessionManager sessionManager = SessionManager.getInstance();

    private AuthService authService = new AuthService();

    private ResponseService responseService = new ResponseServiceImpl();

    protected void channelRead0(ChannelHandlerContext ctx, Packet msg) throws Exception {
        Header header = msg.getHeader();
        Body body = msg.getBody();

        if(header.getType() == PacketType.HANDSHAKE_REQUEST) {//握手请求,处理登录
            //登录信息
            Login login = (Login) body;
            if(authService.authLogin(login.getUid(), login.getPwd())) {//验证登录信息成功
                //分配新的token
                String token = authService.allocateToken(login.getUid());
                //创建session
                ClientSession session = new ClientSession(ctx.channel(), token, login.getUid());
                //加入会话池
                sessionManager.addSession(session);
                //发回握手应答
                responseService.sendLoginSuccess(session);
            } else {
                //登录失败
                responseService.sendLoginError(ctx.channel());
                //关闭连接
                ctx.close();
            }
        } else {//其他请求
            //验证token
            sessionManager.getSession(body.getSender()).getChannel();

            if(authService.authToken(body.getSender(), body.getToken())) {
                //认证成功，传递请求
                ctx.fireChannelRead(msg);
            } else {
                //认证失败，关闭连接
                responseService.sendAuthError(ctx.channel());
                ctx.close();
            }
        }
    }

}

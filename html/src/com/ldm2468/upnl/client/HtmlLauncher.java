package com.ldm2468.upnl.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.badlogic.gdx.backends.gwt.preloader.Preloader;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.ldm2468.upnl.Upnl;

public class HtmlLauncher extends GwtApplication {
    static final int WIDTH = 480;
    static final int HEIGHT = 320;
    static HtmlLauncher instance;
    public ApplicationListener app;

    @Override
    public GwtApplicationConfiguration getConfig() {
        GwtApplicationConfiguration config = new GwtApplicationConfiguration(WIDTH, HEIGHT);

        Element element = Document.get().getElementById("embed-html");
        VerticalPanel panel = new VerticalPanel();
        panel.setWidth("100%");
        panel.setHeight("100%");
        panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
        element.appendChild(panel.getElement());
        config.rootPanel = panel;
        config.preferFlash = false;

        return config;
    }

    @Override
    public ApplicationListener getApplicationListener() {
        return app;
    }

    @Override
    public ApplicationListener createApplicationListener() {
        instance = this;
        setLogLevel(LOG_NONE);
        setLoadingListener(new LoadingListener() {
            @Override
            public void beforeSetup() {

            }

            @Override
            public void afterSetup() {
                scaleCanvas();
                setupResizeHook();
            }
        });
        return (app = new Upnl());
    }

    void scaleCanvas() {
        Element element = Document.get().getElementById("embed-html");
        int innerWidth = getWindowInnerWidth();
        int innerHeight = getWindowInnerHeight();

        NodeList<Element> nl = element.getElementsByTagName("canvas");

        if (nl != null && nl.getLength() > 0) {
            Element canvas = nl.getItem(0);
            canvas.setAttribute("width", "" + innerWidth + "px");
            canvas.setAttribute("height", "" + innerHeight + "px");
            canvas.getStyle().setWidth(innerWidth, Style.Unit.PX);
            canvas.getStyle().setHeight(innerHeight, Style.Unit.PX);
            canvas.getStyle().setTop(0, Style.Unit.PX);
            canvas.getStyle().setLeft(0, Style.Unit.PX);
            canvas.getStyle().setPosition(Style.Position.ABSOLUTE);
        }
    }

    native int getWindowInnerWidth() /*-{
        return $wnd.innerWidth;
    }-*/;

    native int getWindowInnerHeight() /*-{
        return $wnd.innerHeight;
    }-*/;

    native void setupResizeHook() /*-{
        var htmlLauncher_onWindowResize = $entry(@com.ldm2468.upnl.client.HtmlLauncher::handleResize());
        $wnd.addEventListener('resize', htmlLauncher_onWindowResize, false);
    }-*/;

    public static void handleResize() {
        instance.scaleCanvas();
    }

    @Override
    public Preloader.PreloaderCallback getPreloaderCallback() {
        final Element element = Document.get().getElementById("loading-txt");
        return new Preloader.PreloaderCallback() {
            @Override
            public void update(Preloader.PreloaderState state) {
                element.setInnerText("로딩 중... " + (int) (state.getProgress() * 100) + "%");
            }

            @Override
            public void error(String file) {
                element.setInnerText("에러! 인터넷 연결 상태를 확인해보세요.");
            }
        };
    }
}
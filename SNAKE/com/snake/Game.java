package com.snake;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.*;
import java.applet.Applet;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.net.MalformedURLException;

//主类，即入口
public class Game extends Application {

    //
    Button startBT;
    Button continueBT;
    Button exitBT;
    Button infoBT;
    //
    
    

    Group root = new Group();
    Canvas canvas = new Canvas(Config.WIN_WIDTH, Config.WIN_HEIGHT);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    Driver driver = new Driver(gc);
    
    
    //javafx开始
    @Override
    public void start(Stage primaryStage) throws Exception {

        root.getChildren().add(canvas);
        
        BorderPane borderPane = getPane();
        borderPane.setCenter(root);

        Scene scene = new Scene(borderPane);
        scene.setOnKeyPressed(driver);

        primaryStage.setScene(scene);
        primaryStage.setTitle("贪吃蛇大战");
        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        primaryStage.show();
        new Thread(driver).start();
    }
    
    //主函数
    public static void main(String[] args) {
        launch(args);
    }

    //画图象
    public BorderPane getPane() {
        HBox paneForButtons = new HBox(20);
        startBT = new Button("重新开始");
        continueBT = new Button("暂停/继续");
        exitBT = new Button("退出");
        infoBT = new Button("帮助");
        //给按钮设定监听事件
        startBT.setOnAction(event -> driver.reStart());
        continueBT.setOnAction(event -> driver.pause());
        exitBT.setOnAction(event -> driver.exit());
        infoBT.setOnAction(event -> showInfomationDialog());

        javafx.scene.text.Text gameMode = new javafx.scene.text.Text(10, 10, "难度等级");
        gameMode.setFill(Color.BLUE);
        gameMode.setText("难度等级:");

        RadioButton easyRB = new RadioButton("简单");
        RadioButton normalRB = new RadioButton("正常");
        RadioButton hardRB = new RadioButton("地狱");
        ToggleGroup group = new ToggleGroup();
        easyRB.setToggleGroup(group);
        normalRB.setToggleGroup(group);
        hardRB.setToggleGroup(group);
        //给单选框设定监听事件
        easyRB.setOnMouseClicked(event -> Driver.speed = Config.SPEED_EASY);
        normalRB.setOnMouseClicked(event -> Driver.speed = Config.SPEED_NORMAL);
        hardRB.setOnMouseClicked(event -> Driver.speed = Config.SPEED_HARD);

        paneForButtons.getChildren().addAll(startBT, continueBT, infoBT, exitBT);//将按钮放置在水平面板中
        paneForButtons.getChildren().add(gameMode);
        paneForButtons.getChildren().addAll(easyRB, normalRB, hardRB);
        paneForButtons.setAlignment(Pos.CENTER_LEFT);//将对齐设置为 中左
        paneForButtons.setStyle("-fx-border-color: aquamarine");

        BorderPane pane = new BorderPane();
        pane.setBottom(driver.createView());
        pane.setTop(paneForButtons);
        return pane;
    }

    //展示信息栏
    public void showInfomationDialog() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("给你一点游戏帮助");
                alert.setHeaderText("关于贪吃蛇大战的游戏操作说明");
                alert.setContentText("游戏要拿高分，既要考虑到游戏难度，也要根据自己的姿势水平\n我什么都不说，这是最吼的");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {

                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        });
    }

}

/**
 * 用于蛇和食物的管理，即游戏引擎
 */
class Driver implements Runnable, EventHandler<KeyEvent> {

    enum DirectionType {
        U, D, L, R
    }

    //属性
    private GraphicsContext gc;
    private Snake snake;
    private Wall wall;
    private DirectionType directionType = DirectionType.R;    //默认向右行走
    private Node food;
    private Node Buff1 = BuffFactory.resertBuff();
    private Node Buff2 = BuffFactory.resertBuff();
    private int score = 0;//游戏得分
    private int lengthOfBody = Config.BODY_LENGTH;
    private int currentLevel = 1;
    private int foodCount = 0;
    private int Buff1Count = 0;
    private int Buff2Count = 0;
//    private int Buff1FuncNum=0;

    javafx.scene.text.Text curLevel;
    javafx.scene.text.Text scoreText;
    javafx.scene.text.Text bodyLength;
    javafx.scene.text.Text buff_body;
    javafx.scene.text.Text buff_wall;

    public boolean isPause;    //暂停标识
    private boolean isFirstTime = true;
    public static int speed = Config.SPEED_EASY;

    //蛇的初始属性，出生点
    private int ox = Config.BIRTH_X;        //头
    private int oy = Config.BIRTH_Y;        //头
    private int length = Config.BODY_LENGTH;    //蛇身总长度(包括头)

    public Driver(GraphicsContext gc) {
        // super();
        this.gc = gc;
        this.wall = new Wall(gc);
        this.snake = new Snake(gc, this);
        snake.initAttr(ox, oy, length);
    }


    @Override
    public void run() {
        start();
    }


    public void start() {

        upDateView();//更新视图;
        while (true) {
            if (score == 0 || score == 1000 || score == 2000) {
                wall.chooseMap(score, this);
            }

            if (isPause) {
                if (!snake.isAlive()) {
                    if (isFirstTime) {
                        playSoundOfGame(Config.FILE_PATH_HIT);
                        showAlertDialog();
                        isFirstTime = false;
                    }
                }
            } else {
                if (score < 1000)
                    wall.chooseMap(0, this);
                else if (score >= 1000 && score < 2000)
                    wall.chooseMap(1000, this);
                else
                    wall.chooseMap(2000, this);
                productFood();//生产食物
                if (snake.eatFood(food)) {     //蛇吃到食物
                    playSoundOfGame(Config.FILE_PATH_EAT);
                    foodCount = 0;
                    snake.grow();    //变长，增加一个节点
                    upDateScore();
                    upDateView();
                }


                if (score % 600 == 0) {
                    productBuff1();
                }
                if (snake.eatFood(Buff1)) {
                    playSoundOfGame(Config.FILE_PATH_BUFF1);
                    Buff1Count = 0;
                    snake.Buff1FucNumAdd();
                    System.out.println("Buff1 num: " + snake.GetBuff1FucNum());
                    Buff1 = BuffFactory.resertBuff();
                    snake.grow();    //变长，增加一个节点
                    upDateScore();
                    upDateView();
                }
                //换成600
                if ((score + 200) % 600 == 0) {
                    productBuff2();
                }
                if (snake.eatFood(Buff2)) {
                    playSoundOfGame(Config.FILE_PATH_BUFF2);
                    Buff2Count = 0;
                    wall.Buff2FucNumAdd();
                    System.out.println("Buff2 num: " + wall.GetBuff2FucNum());
                    Buff2 = BuffFactory.resertBuff();
                    snake.grow();    //变长，增加一个节点
                    upDateScore();
                    upDateView();
                }

                snake.move(wall);
                if (snake.GetBuff1FucNum() == 0) {
                    //     upDateScore();
                    upDateView();
                }
                if (wall.GetBuff2FucNum() == 0) {
                    //      upDateScore();
                    upDateView();
                }
                wall.hitWall(snake);
            }
            try {
                Thread.sleep(speed * 50L);    //控制蛇的行走速度
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (isPause) {
            isPause = false;
        } else {
            isPause = true;
        }
    }

    public void reStart() {
        /*
         * 重新开始，需要一下几个步骤：
		 * 1、清除屏幕上所有图形
		 * 2、蛇重置
		 * 3、重新生产食物
		 */
        isPause = true;
        gc.clearRect(food.getX(), food.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        gc.clearRect(Buff1.getX(), Buff1.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        gc.clearRect(Buff2.getX(), Buff2.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        snake.alive();
        snake.initAttr(ox, oy, length);
        isPause = false;
        resetDate();
        upDateView();
        productFood();
    }

    //产生底部计数视图
    public HBox createView() {
        HBox paneForText = new HBox(25);
        javafx.scene.text.Text situation = new javafx.scene.text.Text(25, 25, "游戏实况：");
        curLevel = new javafx.scene.text.Text(25, 25, "");
        scoreText = new javafx.scene.text.Text(25, 25, "");
        bodyLength = new javafx.scene.text.Text(25, 25, "");
        buff_body = new javafx.scene.text.Text(25, 25, "绿色穿身BUFF");
        buff_wall = new javafx.scene.text.Text(25, 25, "红色穿墙BUFF");
        curLevel.setFont(Font.font("Courier", FontWeight.BLACK, 20));
        curLevel.setFill(Config.TEXT_COLOR);
        scoreText.setFont(Font.font("Courier", FontWeight.BLACK, 20));
        bodyLength.setFont(Font.font("Courier", FontWeight.BLACK, 20));
        bodyLength.setFill(Config.BUTTON_COLOR);
        buff_body.setFont(Font.font("Courier", FontWeight.BLACK, 20));
        buff_wall.setFont(Font.font("Courier", FontWeight.BLACK, 20));


        paneForText.getChildren().addAll(situation, curLevel, scoreText, bodyLength, buff_body, buff_wall);
        paneForText.setAlignment(Pos.CENTER);
        return paneForText;
    }

    //更新底部视图的数据
    public void upDateScore() {
        lengthOfBody = snake.getBodyLength();
        score += 200;//每吃一个食物，得一百分
        if (score == 1000 || score == 2000) {
            currentLevel++;
            playSoundOfGame(Config.FILE_PATH_UPDATE);
        }
    }


    //重置数据
    public void resetDate() {
        // score = 0;
        lengthOfBody = Config.BODY_LENGTH;
        //    currentLevel = 1;
        foodCount = 0;
        Buff1Count = 0;
        Buff2Count = 0;
        snake.setBuff1FucNum();
        wall.setBuff2FucNum();
//        Buff1FuncNum=0;
    }

    //更新底部视图
    public void upDateView() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                curLevel.setText("当前关卡:" + String.valueOf(currentLevel));
                scoreText.setText("得分:" + String.valueOf(score));
                bodyLength.setText("蛇身长度:" + String.valueOf(lengthOfBody));
                //               buff_body.setText(value);

                //变颜色
                //如果当前还存在Buff，字体变颜色
                if (snake.GetBuff1FucNum() > 0) {
                    buff_body.setFill(Config.BUFF1_COLOR);
                }
                if (snake.GetBuff1FucNum() == 0) {
                    buff_body.setFill(Color.BLACK);
                }
                if (wall.GetBuff2FucNum() > 0) {
                    buff_wall.setFill(Config.BUFF2_COLOR);
                }
                if (wall.GetBuff2FucNum() == 0) {
                    buff_wall.setFill(Color.BLACK);
                }

            }
        });
    }

    public void showAlertDialog() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("询问对话框");
                alert.setHeaderText("人生总有不如意的时候，请坐和放宽");
                alert.setContentText("游戏结束，是否重新开始?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    reStart();
                    isFirstTime = true;
                    System.out.println("重新运行");
                } else {
                    exit();
                    // ... user chose CANCEL or closed the dialog
                }
            }
        });
    }

    public void playSoundOfGame(String s) {
        try {
            File f = new File(s); //绝对路径
            URI uri = f.toURI();
            java.net.URL url = uri.toURL(); //解析路径
            java.applet.AudioClip aau = Applet.newAudioClip(url);
            aau.play();  //单曲循环
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit() {
        System.exit(0);
    }

    //产生食物
    public void productFood() {
        if (foodCount != 0)
            return;

        do {
            //factory只负责食物生产，不进行位置校验
            food = FoodFactory.product();
        } while (snake.isBody(food) || wall.isWall(food) || food.equals(Buff1));

        System.out.println("Food Location: " + food);
        gc.setFill(Config.FOOD_COLOR);
        gc.fillRect(food.getX(), food.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        
        foodCount = 1;
    }

    //产生Buff1
    public void productBuff1() {

        if (Buff1Count != 0)
            return;

        do {
            //factory只负责Buff1生产，不进行位置校验
            Buff1 = BuffFactory.product();
        } while (snake.isBody(Buff1) || wall.isWall(Buff1) || Buff1.equals(food) || Buff1.equals(Buff2));

        System.out.println("Buff1 Location: " + Buff1);

        gc.setFill(Config.BUFF1_COLOR);
        gc.fillRect(Buff1.getX(), Buff1.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);

        Buff1Count = 1;
    }

    public void productBuff2() {

        if (Buff2Count != 0)
            return;

        do {
            //factory只负责Buff1生产，不进行位置校验
            Buff2 = BuffFactory.product();
        } while (snake.isBody(Buff2) || wall.isWall(Buff2) || Buff2.equals(food) || Buff2.equals(Buff1));

        System.out.println("Buff2 Location: " + Buff2);

        gc.setFill(Config.BUFF2_COLOR);
        gc.fillRect(Buff2.getX(), Buff2.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);

        Buff2Count = 1;
    }

    @Override
    public void handle(KeyEvent event) {
        KeyCode code = event.getCode();
        //按键配置可以在config中修改
        if (code == Config.UP || code == KeyCode.W) {
            if (this.directionType != DirectionType.D) {
                this.directionType = DirectionType.U;
                snake.toUp();
            }
        } else if (code == Config.DOWN || code == KeyCode.S) {
            if (this.directionType != DirectionType.U) {
                this.directionType = DirectionType.D;
                snake.toDown();
            }
        } else if (code == Config.LEFT || code == KeyCode.A) {
            if (this.directionType != DirectionType.R) {
                this.directionType = DirectionType.L;
                snake.toLeft();
            }
        } else if (code == Config.RIGHT || code == KeyCode.D) {
            if (this.directionType != DirectionType.L) {
                this.directionType = DirectionType.R;
                snake.toRight();
            }
        } else if (code == Config.PAUSE) {
            pause();
        } else if (code == Config.RESTART) {
            reStart();
        } else {
            System.out.println("Invaild Key!");
        }
    }


}


//用于管理墙
class Wall {
    private List<Node> walls;
    private List<Node> iniwall;
    private GraphicsContext gc;
    private int Buff2FucNum = 0;

    private boolean t1 = true;
    private boolean t2 = true;
    private boolean t3 = true;

    public Wall(GraphicsContext gc) {
        super();
        this.gc = gc;
        this.walls = new ArrayList<Node>();
        this.iniwall = new ArrayList<Node>();
    }

    public void initMap() {
        for (int i = 0; i < 80; i++) {
            walls.add(new Node(0 + 10 * i, 0));
            iniwall.add(new Node(0 + 10 * i, 0));
        }
        for (int i = 0; i < 48; i++) {
            walls.add(new Node(0, 0 + 10 * i));
            iniwall.add(new Node(0, 0 + 10 * i));
        }
        for (int i = 0; i < 80; i++) {
            walls.add(new Node(790 - 10 * i, 470));
            iniwall.add(new Node(790 - 10 * i, 470));
        }
        for (int i = 0; i < 48; i++) {
            walls.add(new Node(790, 470 - 10 * i));
            iniwall.add(new Node(790, 470 - 10 * i));
        }
        //paintMap();
    }

    public void chooseMap(int score, Driver driver) {

        switch (score) {
            case 0:
                if (t1) {
                    map1();
                    t1 = false;
                }
                break;
            case 1000:
                if (t2) {
                    clearWall();
                    map2();
                    driver.reStart();
                    t2 = false;
                }
                break;
            case 2000:
                if (t3) {
                    clearWall();
                    map3();
                    driver.reStart();
                    t3 = false;
                }
                break;
            //default:  return;
        }
        paintMap();

    }


    public void map1() {
        initMap();
        for (int i = 0; i < 47; i++) {
            if (i > 11 && i < 18 || i > 29 && i < 36)
                continue;
            walls.add(new Node(390, i * 10));
        }
        for (int i = 0; i < 80; i++) {
            if (i > 21 && i < 29 || i > 49 && i < 57)
                continue;
            walls.add(new Node(0 + i * 10, 230));
        }
    }

    public void map2() {
        initMap();
        for (int i = 0; i < 40; i++)
            walls.add(new Node(260, i * 10));

        for (int i = 0; i < 40; i++)
            walls.add(new Node(530, 80 + i * 10));

        for (int i = 0; i < 27; i++)
            walls.add(new Node(i * 10, 430));

        for (int i = 0; i < 27; i++)
            walls.add(new Node(530 + i * 10, 40));
    }

    public void map3() {
        initMap();
        for (int i = 0; i < 42; i++) {
            if (i > 12 && i < 29)
                continue;
            walls.add(new Node(260, 30 + i * 10));
        }
        for (int i = 0; i < 42; i++) {
            if (i > 12 && i < 29)
                continue;
            walls.add(new Node(530, 30 + i * 10));
        }
        for (int i = 0; i < 52; i++) {
            if (i > 12 && i < 39)
                continue;
            walls.add(new Node(140 + i * 10, 150));
        }
        for (int i = 0; i < 52; i++) {
            if (i > 12 && i < 39)
                continue;
            walls.add(new Node(140 + i * 10, 320));
        }
        for (int i = 0; i < 42; i++)
            walls.add(new Node(390, 30 + i * 10));
    }

    public void clearWall() {
        for (Node node : walls) {
            gc.clearRect(node.getX(), node.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        }
        walls.clear();
    }

    public void setBuff2FucNum() {
        Buff2FucNum = 0;
    }

    public void Buff2FucNumAdd() {
        Buff2FucNum++;
    }

    public int GetBuff2FucNum() {
        return Buff2FucNum;
    }

    public void paintMap() {
        gc.setFill(Config.WALL_COLOR);
        for (Node node : walls) {
            gc.fillRect(node.getX(), node.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        }
    }

    public void hitWall(Snake snake) {
        if (walls.contains(snake.getHead())) {
            if (Buff2FucNum == 0 || iniwall.contains(snake.getHead()))
                snake.setState();
            else
                Buff2FucNum--;
        }

    }

    //用于在蛇类中判断要不要去除节点
    public boolean WallHitTail(Snake snake) {
        if (walls.contains(snake.getTail())) {
            return true;
        } else
            return false;
    }


    public boolean isWall(Node food) {

        return walls.contains(food);
    }
}

//用于管理蛇
class Snake {

    //蛇身由一系列的节点组成
    private List<Node> bodys;
    //画板，用于绘制蛇节点
    private GraphicsContext gc;
    //控制蛇运动
    private Driver driver;

    private int Buff1FuncNum = 0;
    private int state = 1;
    private Driver.DirectionType direction = Driver.DirectionType.R;

    public Snake(GraphicsContext gc, Driver driver) {
        super();
        this.gc = gc;
        this.driver = driver;
        this.bodys = new ArrayList<Node>();
    }

    public void setBuff1FucNum() {
        Buff1FuncNum = 0;
    }

    public int GetBuff1FucNum() {
        return Buff1FuncNum;
    }

    public void Buff1FucNumAdd() {
        Buff1FuncNum++;
    }

    public void move(Wall wall1) {
        Node head = bodys.get(0);
        Node newHead = new Node();

        //蛇的移动其实就是蛇头前一个节点变为头，
        //尾节点去掉，尾节点前一个节点变为尾节点
        //根据前进的方向决定新增的头的位置
        switch (direction) {
            case U:
                newHead.setY(head.getY() - Config.UNIT_SIZE);
                newHead.setX(head.getX());
                break;
            case D:
                newHead.setY(head.getY() + Config.UNIT_SIZE);
                newHead.setX(head.getX());
                break;
            case L:
                newHead.setX(head.getX() - Config.UNIT_SIZE);
                newHead.setY(head.getY());
                break;
            case R:
                newHead.setX(head.getX() + Config.UNIT_SIZE);
                newHead.setY(head.getY());
                break;
        }

        //撞到了身体
        if (bodys.contains(newHead)) {
            if (Buff1FuncNum == 0) {
                driver.pause();
                state = 0;
                return;
            } else
                Buff1FuncNum--;
        }

        bodys.add(0, newHead);

        Node tail = bodys.get(bodys.size() - 1);
        //如果尾部在墙上，不清除
        if (!wall1.WallHitTail(this))
            gc.clearRect(tail.getX(), tail.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        else {
            gc.setFill(Config.WALL_COLOR);
            gc.fillRect(tail.getX(), tail.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        }

        bodys.remove(bodys.size() - 1);

        paint();
    }

    public void grow() {
        Node tail = bodys.get(bodys.size() - 1);
        Node node = new Node();
        //新增的节点在尾部，与头部的新增刚好相反
        switch (direction) {
            case U:
                node.setY(tail.getY() + Config.UNIT_SIZE);
                node.setX(tail.getX());
                break;
            case D:
                node.setY(tail.getY() - Config.UNIT_SIZE);
                node.setX(tail.getX());
                break;
            case L:
                node.setX(tail.getX() + Config.UNIT_SIZE);
                node.setY(tail.getY());
                break;
            case R:
                node.setX(tail.getX() - Config.UNIT_SIZE);
                node.setY(tail.getY());
                break;
        }

        bodys.add(node);
        paint();
        //通知driver重新生产食物
        driver.productFood();
    }

    public Node getHead() {
        return bodys.get(0);
    }

    public Node getTail() {
        return bodys.get(bodys.size() - 1);
    }

    public void setState() {
        state = 0;
        driver.pause();
    }

    public boolean eatFood(Node food) {
        //头部和食物重叠即表示吃到了食物
        if (bodys.get(0).equals(food)) {
            gc.clearRect(food.getX(), food.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
            return true;
        }
        return false;
    }

    public void toUp() {
        direction = Driver.DirectionType.U;
        // move();
    }

    public void toDown() {
        direction = Driver.DirectionType.D;
        // move();
    }

    public void toLeft() {
        direction = Driver.DirectionType.L;
        // move();
    }

    public void toRight() {
        direction = Driver.DirectionType.R;
        // move();
    }

    //初始化在x,y位置往左产生长度为length的蛇
    public void initAttr(double x, double y, int length) {
        for (int i = 0; i < length; i++) {
            bodys.add(new Node(x - 10 * i, y));
        }
    }

    public boolean isBody(Node food) {
        return bodys.contains(food);
    }

    public boolean isAlive() {
        return state == 1 ? true : false;
    }

    /**
     * 蛇的重置，清除画布上的蛇，以及身体节点的清空
     */
    public void alive() {
        for (Node node : bodys) {
            gc.clearRect(node.getX(), node.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        }
        this.bodys.clear();
        this.direction = Driver.DirectionType.R;
        this.state = 1;
    }

    //画蛇
    public void paint() {



        for (int i = 0; i <bodys.size() ; i++) {
            Node node=bodys.get(i);
            if(i%2==0){
                if(i==0){
                    gc.setFill(Config.BODY_COLOR_HEAD);
                }else {
                    gc.setFill(Config.BODY_COLOR_1);
                }
            } else {
                gc.setFill(Config.BODY_COLOR_2);
            }
            gc.fillRect(node.getX(), node.getY(), Config.UNIT_SIZE, Config.UNIT_SIZE);
        }

    }

    public int getBodyLength() {
        return bodys.size();
    }//蛇身长度
}

//食物工厂，产生食物的坐标
class FoodFactory {
    private static Random r = new Random();

    public static Node product() {
        int x, y;
        do {
            x = r.nextInt(Config.WIN_WIDTH - Config.UNIT_SIZE);
            y = r.nextInt(Config.WIN_HEIGHT - Config.UNIT_SIZE);
            //循环条件确保生成的食物坐标能与蛇头重叠
        } while ((x % Config.UNIT_SIZE != 0) || (y % Config.UNIT_SIZE != 0));

        return new Node(x, y);
    }
}

//
class BuffFactory {
    private static Random r = new Random();

    public static Node product() {
        int x, y;
        do {
            x = r.nextInt(Config.WIN_WIDTH - Config.UNIT_SIZE);
            y = r.nextInt(Config.WIN_HEIGHT - Config.UNIT_SIZE);
            //循环条件确保生成的食物坐标能与蛇头重叠
        } while ((x % Config.UNIT_SIZE != 0) || (y % Config.UNIT_SIZE != 0));

        return new Node(x, y);
    }

    public static Node resertBuff() {

        int x, y;
        x = Config.WIN_WIDTH;
        y = Config.WIN_HEIGHT;
        return new Node(x, y);
    }
}


//节点，用于判断坐标
class Node {

    protected double x;
    protected double y;

    public Node() {
    }

    public Node(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        //list.contains用到了该方法，需要重写
        Node node = (Node) obj;
        if (this.x == node.getX() && this.y == node.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "[" + this.x + ".." + this.y + "]";
    }
}

/**
 * 配置
 */
class Config {

    public static final int WIN_WIDTH = 800;
    public static final int WIN_HEIGHT = 480;
    public static final int UNIT_SIZE = 10;

    public static final KeyCode UP = KeyCode.UP;
    public static final KeyCode DOWN = KeyCode.DOWN;
    public static final KeyCode LEFT = KeyCode.LEFT;
    public static final KeyCode RIGHT = KeyCode.RIGHT;
    public static final KeyCode PAUSE = KeyCode.P;
    public static final KeyCode RESTART = KeyCode.R;

    public static final Color WALL_COLOR = Color.rgb(63,81,181);
    public static final Color BODY_COLOR_1= Color.rgb(121, 85, 72);
    public static final Color BODY_COLOR_2 = Color.rgb(100, 221, 23);
    public static final Color BODY_COLOR_HEAD = Color.rgb(255, 87, 34);


    public static final Color FOOD_COLOR = Color.BLACK;
    public static final Color BUTTON_COLOR = Color.CHOCOLATE;
    public static final Color TEXT_COLOR = Color.DARKORANGE;
    public static final Color BUFF1_COLOR = Color.rgb(0, 150, 136);
    public static final Color BUFF2_COLOR = Color.rgb(255, 23, 68);


    public static final int BIRTH_X = 150;
    public static final int BIRTH_Y = 50;
    public static final int BODY_LENGTH = 10;
    public static final int SPEED_EASY = 3;
    public static final int SPEED_NORMAL = 2;
    public static final int SPEED_HARD = 1;

    public static final String FILE_PATH_EAT = "eat.wav";
    public static final String FILE_PATH_HIT = "hitWall.wav";
    public static final String FILE_PATH_UPDATE = "update.wav";
    public static final String FILE_PATH_BUFF1 = "buff1.wav";
    public static final String FILE_PATH_BUFF2 = "buff2.wav";
    
}



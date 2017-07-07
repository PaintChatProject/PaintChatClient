import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.ObjectOutputStream;

public class UserInterface extends JFrame {

    static UserInterface userInterface=new UserInterface();

    //그림판
    JPanel paintPanel;
    JPanel colorPanel;

    JButton[] colorBtn = new JButton[7];
    String[] colorBtnText = {"red", "orange", "yellow", "green", "blue", "pink"};
    Color[] color = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.pink};
    JButton eraser, clear, fillColor, image;

    PaintCanvas paintCanvas;
    JComboBox jcb;

    // 이미지파일 불러오기
    JFileChooser fileChooser;
    JFrame imageFrame;

    //채팅
    JPanel chatPanel;
    JPanel chatInputPanel;
    JTextArea chatArea;
    JTextField chatNameField;
    JTextField chatInputField;
    JButton sendBtn;
    SendData sendData;

    public static UserInterface getInstance(){
        return userInterface;
    }

    public void showUI() {
        paintCanvas = PaintCanvas.getInstance();
        colorPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < color.length; i++) {
            colorBtn[i] = new JButton(colorBtnText[i]);
            Color indexColor = color[i];
            colorBtn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                }
            });
            colorBtn[i].setOpaque(true);
            colorBtn[i].setBackground(color[i]);
            colorPanel.add(colorBtn[i]);
        }

        eraser = new JButton("지우개");
        eraser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        colorPanel.add(eraser);

        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //paintCanvas.clearBackground();
                sendData.sendPaintData(new PaintData(-10, -10, -10));
            }
        });
        colorPanel.add(clear);

        fillColor = new JButton("FillColor");
        fillColor.setOpaque(true);
        fillColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //paintCanvas.clearBackground();
                sendData.sendPaintData(new PaintData(-10, -10, -10));
            }
        });
        colorPanel.add(fillColor);

        image = new JButton("Image");
        image.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                imageFrame = new JFrame();
                javax.swing.filechooser.FileFilter imageFileFilter = NewFileFilter("Image Files", new String[] { "png", "jpg" });
                fileChooser.addChoosableFileFilter(imageFileFilter);
                fileChooser.setFileFilter(imageFileFilter);
                int returnVal = fileChooser.showOpenDialog(imageFrame);
                if (returnVal == JFileChooser.APPROVE_OPTION) { //열기 버튼을 누르면
                    File file = fileChooser.getSelectedFile();
                    ImageIcon imageIcon = null;
                    try {
                        imageIcon=new ImageIcon(ImageIO.read(file));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //PaintCanvas.getInstance().setBackgroundImage(img, 0, 0, null);
                    sendData.sendImageData(imageIcon);
                }
            }
            // 파일 필터 (사진만 선택할 수 있도록)
            private javax.swing.filechooser.FileFilter NewFileFilter(final String desc, final String[] allowed_extensions) {
                return new javax.swing.filechooser.FileFilter() {
                    @Override
                    public boolean accept(java.io.File f) {
                        if (f.isDirectory()) {
                            return true;
                        }
                        int pos = f.getName().lastIndexOf('.');
                        if (pos == -1) {
                            return false;
                        } else {
                            String extension = f.getName().substring(pos + 1);
                            for (String allowed_extension : allowed_extensions) {
                                if (extension.equalsIgnoreCase(allowed_extension)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                    }

                    @Override
                    public String getDescription() {
                        return desc;
                    }
                };
            }
        });
        colorPanel.add(image);

        String[] str = {"10", "15", "20", "25", "30"};
        jcb = new JComboBox(str);

        jcb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        jcb.setSelectedItem(str[0]);
        colorPanel.add(jcb);

        paintPanel = new JPanel(new BorderLayout());
        paintPanel.add(colorPanel, BorderLayout.NORTH);
        paintPanel.add(paintCanvas, BorderLayout.CENTER);

        ///

        chatPanel = new JPanel(new BorderLayout());
        chatArea = new JTextArea(15, 40);
        chatArea.setLineWrap(true); //자동 개행
        chatArea.setWrapStyleWord(true); //행을 넘길 때 행의 마지막 단어가 두행에 걸쳐 나뉘지 않도록 하기
        chatArea.setEditable(false);
        chatArea.setVisible(true);
        JScrollPane qScroller = new JScrollPane(chatArea);
        qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); //수직 스크롤바 표시 정책 : 항상 보여주기
        qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);// 수평 스크롤바 표시 정책 : 항상 보여주기

        chatInputPanel = new JPanel(new FlowLayout());
        chatNameField= new JTextField(10);
        chatInputField = new JTextField(20);
        chatInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendBtn.doClick();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        sendBtn = new JButton("Send");
        sendData=SendData.getInstance();
        sendData.setOutputStream(SocketData.getInstance().getSocket());
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatInputField.getText().length() > 0) {
                    String chatName=chatNameField.getText();
                    String chatInput=chatInputField.getText();
                    ChatData chatData=(chatName.length()==0)?new ChatData(chatInput):new ChatData(chatName,chatInput);
                    //AddChat(chatData);
                    chatInputField.setText("");
                    sendData.sendChatData(chatData);
                }
            }
        });
        chatPanel.add(qScroller, BorderLayout.CENTER);
        chatPanel.add(chatInputPanel, BorderLayout.SOUTH);

        chatInputPanel.add(chatNameField);
        chatInputPanel.add(chatInputField);
        chatInputPanel.add(sendBtn);

        setLayout(new BorderLayout());
        add(paintPanel, BorderLayout.WEST);
        add(chatPanel, BorderLayout.EAST);

        setBounds(100, 100, 1600, 600);
        setResizable(true);
        setVisible(true);

        paintCanvas.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PaintData paintData=new PaintData(e.getX() - 20 / 2,e.getY() - 20 / 2,20);
                paintCanvas.addPaint(paintData);
                sendData.sendPaintData(paintData);
            }
        });

        paintCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                PaintData paintData=new PaintData(e.getX() - 20 / 2,e.getY() - 20 / 2,20);
                //paintCanvas.addPaint(paintData);
                sendData.sendPaintData(paintData);
            }
        });
    }


    public void AddChat(ChatData chatData){
        chatArea.append("["+chatData.getName()+"] "+chatData.getMessage()+"\n");
    }
}

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class UserInterface extends JFrame {

    static UserInterface userInterface=null;

    //그림판
    JPanel paintPanel;
    JPanel colorPanel;

    JButton[] colorBtn = new JButton[7];
    String[] colorBtnText = {"red", "orange", "yellow", "green", "blue", "pink"};
    Color[] color = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.pink};
    JButton eraser, clear, fillColor, fileChooser;

    PaintCanvas paintCanvas;
    JComboBox jcb;

    //채팅
    JPanel chatPanel;
    JPanel chatInputPanel;
    JTextArea chatArea;
    JTextField chatInputField;
    JButton sendBtn;

    public static UserInterface getInstance(){
        if(userInterface==null) return new UserInterface();
        return userInterface;
    }

    public UserInterface() {
        paintCanvas = new PaintCanvas();
        colorPanel = new JPanel(new FlowLayout());
        for (int i = 0; i < color.length; i++) {
            colorBtn[i] = new JButton(colorBtnText[i]);
            Color indexColor = color[i];
            colorBtn[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    paintCanvas.setC(indexColor);
                    fillColor.setBackground(indexColor);
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
                paintCanvas.setC(paintCanvas.getBackground());
            }
        });
        colorPanel.add(eraser);

        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics g = paintCanvas.getGraphics();
                g.clearRect(0, 0, paintCanvas.getWidth(), paintCanvas.getHeight());
            }
        });
        colorPanel.add(clear);

        fillColor = new JButton("FillColor");
        fillColor.setOpaque(true);
        fillColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paintCanvas.setBackgroundColor(paintCanvas.getC());
            }
        });
        colorPanel.add(fillColor);


        fileChooser = new JButton("Image Load");
        fileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MyFrame my = new MyFrame();
            }
        });
        colorPanel.add(fileChooser);

        String[] str = {"10", "15", "20", "25", "30"};
        jcb = new JComboBox(str);

        jcb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String s = (String) jcb.getSelectedItem();
                paintCanvas.setSize(Integer.parseInt(s));
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
        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chatInputField.getText().length() > 0) {
                    chatArea.append(chatInputField.getText() + "\n");
                    chatInputField.setText("");
                }
            }
        });
        chatPanel.add(qScroller, BorderLayout.CENTER);
        chatPanel.add(chatInputPanel, BorderLayout.SOUTH);

        chatInputPanel.add(chatInputField);
        chatInputPanel.add(sendBtn);

        setLayout(new BorderLayout());
        add(paintPanel, BorderLayout.WEST);
        add(chatPanel, BorderLayout.EAST);

        setBounds(100, 100, 1200, 600);
        setResizable(true);
        setVisible(true);

        paintCanvas.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                PaintData paintData=new PaintData(e.getX() - paintCanvas.getSiz() / 2,e.getY() - paintCanvas.getSiz() / 2,paintCanvas.getC(),paintCanvas.getSiz());
                paintCanvas.addPaint(paintData);
            }
        });
    }

    class MyFrame implements ActionListener {
        private JFrame frm = new JFrame();
        private JFileChooser fileChooser = new JFileChooser();
        private JPanel northPanel = new JPanel();
        private JPanel centerPanel = new JPanel();
        private JButton openBtn = new JButton();
        private JButton saveBtn = new JButton();
        private JLabel fileLabel = new JLabel();
        private JTextField address = new JTextField();

        public MyFrame() {
            //기본 컴포넌트 설정
            fileLabel.setText("파일 경로");
            address.setColumns(30);
            openBtn.setText("열기");
            saveBtn.setText("저장");

            //액션 리스너 장착
            openBtn.addActionListener(this);
            saveBtn.addActionListener(this);

            //각 패널 설정 및 컴포넌트 장착
            centerPanel.add(fileLabel);
            centerPanel.add(address);
            northPanel.setLayout(new GridLayout(0, 2));
            northPanel.add(openBtn);
            northPanel.add(saveBtn);

            //프레임에 패널 장착
            frm.add(northPanel, "North");
            frm.add(centerPanel, "Center");

            //기본 프래임 셋팅
            frm.setTitle("JFileChooser 예제");
            frm.setLocation(120, 120);
            frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frm.pack();
            frm.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == openBtn) {
                int returnVal = fileChooser.showOpenDialog(frm);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    //열기 버튼을 누르면
                    File file = fileChooser.getSelectedFile();
                    address.setText(file.toString() + "을 오픈합니다");
                } else {
                    //취소 버튼을 누르면
                    address.setText("파일을 열지 못했습니다");
                    System.out.println("취소합니다");
                }
            } else if (e.getSource() == saveBtn) {
//                int returnVal = fileChooser.showSaveDialog(frm);
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    //취소 버튼을 누르면
                    File file = fileChooser.getSelectedFile();
                    Toolkit toolkit = Toolkit.getDefaultToolkit();
                    Image image = toolkit.getImage(String.valueOf(file));
                    Graphics g = paintCanvas.getGraphics();
                    g.drawImage(image, 0, 0,null);
//                } else {
                    //취소 버튼을 누르면
//                    address.setText("파일을 저장하지 못했습니다");
//                }
            }
        }
    }
}

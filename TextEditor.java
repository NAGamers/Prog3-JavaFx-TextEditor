import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javafx.stage.Stage;


import java.io.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Smady91 on 03.05.2017.
 */
public class TextEditor extends Application{
    public int i = 0;
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,800,600);
        TextArea text = new TextArea("");

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        // File menu - new, save, exit
        Menu fileMenu = new Menu("Menü");
        MenuItem newMenuItem = new MenuItem("Datei neu");
        newMenuItem.setOnAction(actionEvent -> text.setText(""));
        MenuItem openMenuItem = new MenuItem("Datei öffnen");
        openMenuItem.setOnAction(actionEvent -> {
            try {
                open(primaryStage, text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        MenuItem saveMenuItem = new MenuItem("Datei speichern unter");
        saveMenuItem.setOnAction(actionEvent -> save(primaryStage,text));
        MenuItem exitMenuItem = new MenuItem("Programm beenden");
        exitMenuItem.setOnAction(actionEvent -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, openMenuItem, saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);

        Menu sMenu = new Menu("Suche");
        MenuItem sucheMenuItem = new MenuItem("Suche");
        sucheMenuItem.setOnAction(actionEvent -> suche(text));
        MenuItem esucheMenuItem = new MenuItem("Suche und ersetz");
        esucheMenuItem.setOnAction(actionEvent -> esuche(text));
        sMenu.getItems().addAll(sucheMenuItem, esucheMenuItem);
        menuBar.getMenus().addAll(sMenu);


                root.setCenter(text);
        primaryStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        primaryStage.show();
    }


    public void open(Stage primaryStage,TextArea text) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("open File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.setInitialFileName("file.txt");
        File file = fileChooser.showOpenDialog(primaryStage);

        if(file != null){
            InputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder out = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                out.append(line + "\n");
            }
            text.setText(out.toString());
            reader.close();
        }
           };



    public void save(Stage primaryStage, TextArea text){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
        fileChooser.setInitialFileName("file.txt");
        File file = fileChooser.showSaveDialog(primaryStage);

        if(file != null){
        try {
            FileWriter fileWriter = null;
            fileWriter = new FileWriter(file);
            fileWriter.write(text.getText());
            fileWriter.close();
        } catch (IOException ex) {

        }}
    };

    public void suche(TextArea fullText){
        Stage sucheStage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,400,100);
        HBox textBox = new HBox(4);
        textBox.setAlignment(Pos.BOTTOM_CENTER);
        textBox.getChildren().add(new Label("Suche nach"));
        TextField stext = new TextField("");
        textBox.getChildren().add(stext);
        root.setTop(textBox);
        CheckBox checkBox = new CheckBox("Groß- / Kleinschreibung beachten");
        root.setCenter(checkBox);
        HBox bBox = new HBox(4);
        Button bsuche = new Button("Suche");
        Label eLabel = new Label("");
        List<Integer> fList = new ArrayList<Integer>(1);

        bsuche.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   asuche(checkBox,fList,fullText,stext,eLabel);
                               }
                           }
        );

        Button bnext = new Button("Next");
        bnext.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent e) {
                                  if(i == fList.size()-1){
                                      i=0;
                                  }else{
                                      i ++;
                                  }
                                  fullText.selectRange(fList.get(i), fList.get(i)+ stext.getText().length());

                              }
                          }
        );

        Button bPrevious  = new Button("Previous");
        bPrevious.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent e) {
                                      if(i == 0){
                                          i=fList.size()-1;
                                      }else{
                                          i --;
                                      }
                                      fullText.selectRange(fList.get(i), fList.get(i)+ stext.getText().length());

                                  }
                              }
        );

        bBox.getChildren().add(bsuche);
        bBox.getChildren().add(bPrevious);
        bBox.getChildren().add(bnext);
        bBox.getChildren().add(eLabel);
        root.setBottom(bBox);
        sucheStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        sucheStage.show();
    };

    public void esuche(TextArea fullText){
        Stage sucheStage = new Stage();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root,400,150);
        VBox textboxV = new VBox(2);
        HBox textBox1 = new HBox(4);
        textBox1.setAlignment(Pos.BOTTOM_CENTER);
        textBox1.getChildren().add(new Label("Suche nach"));
        TextField stext = new TextField("");
        textBox1.getChildren().add(stext);
        HBox textBox2 = new HBox(4);
        textBox2.setAlignment(Pos.BOTTOM_CENTER);
        textBox2.getChildren().add(new Label("Ersetz Mit"));
        TextField stext2 = new TextField("");
        textBox2.getChildren().add(stext2);
        textboxV.getChildren().add(textBox1);
        textboxV.getChildren().add(textBox2);
        root.setTop(textboxV);
        CheckBox checkBox = new CheckBox("Groß- / Kleinschreibung beachten");
        root.setCenter(checkBox);
        HBox bBox = new HBox(4);
        Button bsuche = new Button("Suche");
        Label eLabel = new Label("");
        List<Integer> fList = new ArrayList<Integer>(1);

        bsuche.setOnAction(new EventHandler<ActionEvent>() {
                               @Override
                               public void handle(ActionEvent e) {
                                   asuche(checkBox,fList,fullText,stext,eLabel);
                               }
                           }
        );


        Button bnext = new Button("Next");
        bnext.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent e) {
                                  if(fList.size() != 0){
                                  if(i == fList.size()-1){
                                      i=0;
                                  }else{
                                      i ++;
                                  }
                                  fullText.selectRange(fList.get(i), fList.get(i)+ stext.getText().length());

                                  }}
                          }
        );

        Button bPrevious  = new Button("Previous");
        bPrevious.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent e) {
                                      if(fList.size() != 0){

                                      if(i == 0){
                                          i=fList.size()-1;
                                      }else{
                                          i --;
                                      }
                                      fullText.selectRange(fList.get(i), fList.get(i)+ stext.getText().length());

                                      }
                                  }
                              }
        );

        Button replace   = new Button("ersetzen");
        replace.setOnAction(new EventHandler<ActionEvent>() {
                              @Override
                              public void handle(ActionEvent e) {
                                  System.out.println(fList.size());
                                  System.out.println(i);
                                  for(int i:fList){
                                      System.out.println(i);
                                  }
                                  if(fList.size() != 0){
                                      fullText.replaceText(fList.get(i), fList.get(i)+ stext.getText().length(),stext2.getText());
                                      fList.remove(i);
                                      if(fList.size() != 0){
                                      checkindex(checkBox,fList,fullText,stext);
                                      }}
                                   if(fList.size() != 0){
                                      if(i >= fList.size()){
                                          i = fList.size() -1 ;
                                      }
                                    fullText.selectRange(fList.get(i), fList.get(i)+ stext.getText().length());
                                }else{
                                    fullText.selectRange(0, 0);
                                }


                              }
                          }
        );

        Button replaceAll  = new Button("alles ersetzen");
        replaceAll.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent e) {
                                      asuche(checkBox,fList,fullText,stext,eLabel);
                                      if(fList.size() != 0){
                                       if(checkBox.isSelected()){
                                          fullText.setText(fullText.getText().replaceAll("(?i)"+stext.getText(), stext2.getText()));
                                      }else{
                                          fullText.setText(fullText.getText().replaceAll(stext.getText(), stext2.getText()));
                                      }
                                          fList.clear();
                                          fullText.selectRange(0, 0);
                                      }


                                      }
                              }
        );


        bBox.getChildren().add(bsuche);
        bBox.getChildren().add(bPrevious);
        bBox.getChildren().add(bnext);
        bBox.getChildren().add(replace);
        bBox.getChildren().add(replaceAll);
        VBox bBoxV = new VBox(2);
        bBoxV.getChildren().add(eLabel);
        bBoxV.getChildren().add(bBox);
        root.setBottom(bBoxV);
        sucheStage.setScene(scene);
        //primaryStage.setFullScreen(true);
        sucheStage.show();
    };

    public void checkindex(CheckBox checkBox,List fList,TextArea fullText,TextField stext){
        if(checkBox.isSelected()){
            fList.clear();
            int x = fullText.getText().toLowerCase().indexOf(stext.getText().toLowerCase());
            fList.add(x);
            while(x >= 0) {
                x = fullText.getText().toLowerCase().indexOf(stext.getText().toLowerCase(), x+1);
                if (x != -1) {
                    fList.add(x);
                }
            }
        }else{
            fList.clear();
            int x = fullText.getText().indexOf(stext.getText());
            fList.add(x);
            while(x >= 0) {
                x = fullText.getText().indexOf(stext.getText(), x+1);
                if (x != -1) {
                    fList.add(x);
                }
            }
        }

    }


    public void asuche(CheckBox checkBox,List<Integer> fList,TextArea fullText,TextField stext,Label eLabel){
        fList.clear();
        i = 0;
        if(checkBox.isSelected()){
            if (stext.getText().toLowerCase() != null && !stext.getText().isEmpty()) {
                int index = fullText.getText().toLowerCase().indexOf(stext.getText().toLowerCase());
                if (index == -1) {
                    eLabel.setText("Search key Not in the text");
                } else {
                    int x = fullText.getText().toLowerCase().indexOf(stext.getText().toLowerCase());
                    fList.add(x);
                    while(x >= 0) {
                        x = fullText.getText().toLowerCase().indexOf(stext.getText().toLowerCase(), x+1);
                        if (x != -1) {
                            fList.add(x);
                        }
                    }
                    eLabel.setText("Found");
                    i = 0;
                    fullText.selectRange(fList.get(i), fList.get(i)+ stext.getText().toLowerCase().length());
                }
            } else {
                eLabel.setText("Missing search key");
                //  errorText.setFill(Color.RED);

            }
        }else{
            if (stext.getText() != null && !stext.getText().isEmpty()) {
                int index = fullText.getText().indexOf(stext.getText());
                if (index == -1) {
                    eLabel.setText("Search key Not in the text");
                } else {
                    int x = fullText.getText().indexOf(stext.getText());
                    fList.add(x);
                    while(x >= 0) {
                        x = fullText.getText().indexOf(stext.getText(), x+1);
                        if (x != -1) {
                            fList.add(x);
                        }
                    }
                    eLabel.setText("Found");
                    i = 0;
                    fullText.selectRange(fList.get(i), fList.get(i)+ stext.getText().length());
                }
            } else {
                eLabel.setText("Missing search key");
                //  errorText.setFill(Color.RED);

            }
        }

    }}

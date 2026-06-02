package minedusty.world.blocks.logic;

import arc.Core;
import arc.Input.TextInput;
import arc.graphics.Color;
import arc.graphics.g2d.*;
import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.scene.ui.Label;
import arc.scene.ui.Slider;
import arc.scene.ui.TextArea;
import arc.scene.ui.layout.*;
import arc.util.*;
import arc.util.pooling.Pools;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.gen.Icon;
import mindustry.graphics.Layer;
import mindustry.ui.Fonts;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.blocks.logic.MessageBlock;

import static arc.Core.settings;
import static mindustry.Vars.*;

/** A message containing block. Akin to the infomatic from Aquarion! */
public class PetroglyphBlock extends MessageBlock{
    public float revealDur = 45f;
    public float startFade = 80f;
    public float endFade = 30f;

    // private static final String S_CHARS = "01"; //ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789#$%&?!
    
    public PetroglyphBlock(String name){
        super(name);
    }

    public class PetroglyphBuild extends MessageBuild{   

        private String resolveRaw(){
            if(message == null || message.length() == 0) return "";
            String rawText = message.toString(); 
            if(rawText.startsWith("@")) return Core.bundle.get(rawText.substring(1));
            return rawText;
        }

        @Override
        public void draw(){
            super.draw();
            if(!enabled) return;
            if(Core.settings.getBool("hidedisplays")) return;
            Draw.z(Layer.overlayUI+1);
            if(Vars.player.unit() != null && !Vars.player.unit().dead()){
                float dst;
                if(settings.getBool("dusty-toggle-mouse-fade")){
                    Vec2 mouse = Core.input.mouseWorld(Core.input.mouseX(), Core.input.mouseY());
                    dst = Mathf.dst(mouse.x, mouse.y, tile.worldx(), tile.worldy());
                } else {
                    dst = Mathf.dst(Vars.player.unit().x, Vars.player.unit().y, tile.worldx(), tile.worldy());
                }
                float dstMulti = settings.getInt("dusty-fade-dist-multi");
                float alpha = 1f - Mathf.clamp((dst - (endFade * dstMulti)) / ((startFade * dstMulti) - (12 * dstMulti)), 0f, 1f);
                message(alpha);
            }
        }

        public void message(float alpha){
            if(renderer.pixelate) return;

            Font font = Fonts.outline;
            GlyphLayout l = Pools.obtain(GlyphLayout.class, GlyphLayout::new);
            boolean ints = font.usesIntegerPositions();
            font.getData().setScale(1 / 4f / Scl.scl(1f));
            font.setUseIntegerPositions(false);

            Boolean empty = message == null || message.length() == 0;
            
            String displayText;
            if(!empty){
                displayText = resolveRaw();
            }else{
                displayText = "[lightgray]" + Core.bundle.get("minedusty.glpyh-empty");
            }

            displayText = UI.formatIcons(displayText);

            l.setText(font, displayText, Color.white, 90f, Align.center, true);
            float offset = 1f;

            float drawY = y + tilesize/2f + l.height/2f + offset;
            Draw.color(0f, 0f, 0f, 0.2f * alpha);
            Fill.rect(x, drawY, l.width + offset*2f, l.height + offset*2f);
            Draw.color( 1f, 1f, 1f, alpha);
            
            Color fontColor = (message.length() == 0 ? Color.lightGray : Color.white).cpy();
            fontColor.a = alpha;
            font.setColor(fontColor);
            font.draw(displayText, x - l.width/2f, drawY + l.height/2f, 90f, Align.left, true);
            font.setUseIntegerPositions(ints);

            font.getData().setScale(1f);
            font.setColor(Color.white);
            Pools.free(l);
        }

        @Override
        public void drawSelect(){
            message(1f);
        }

        @Override
        public void buildConfiguration(Table table){
            boolean canEdit = Vars.state.rules.infiniteResources || Vars.state.rules.editor;
            if(!canEdit){
                table.button(Icon.lock, Styles.cleari, () -> {
                    Vars.ui.showInfo("@minedusty.edit-locked");
                }).size(40f);
                return;
            }
            table.button(Icon.pencil, Styles.cleari, () -> {
                if(mobile){
                    var contents = message;
                    Core.input.getTextInput(new TextInput(){{
                        text = contents.toString();
                        multiline = true;
                        maxLength = maxTextLength;
                        accepted = str -> {
                            if(!str.contentEquals(contents)) configure(str);
                        };
                    }});
                }else{
                    BaseDialog dialog = new BaseDialog("@editmessage");
                    dialog.setFillParent(false);
                    TextArea a = dialog.cont.add(new TextArea(message.toString().replace("\r", "\n"))).size(380f, 160f).get();
                    a.setFilter((textField, c) -> {
                        if(c == '\n'){
                            int count = 0;
                            for(int i = 0; i < textField.getText().length(); i++){
                                if(textField.getText().charAt(i) == '\n'){
                                    count++;
                                }
                            }
                            return count < maxNewlines;
                        }
                        return true;
                    });
                    a.setMaxLength(maxTextLength);
                    dialog.cont.row();
                    dialog.cont.label(() -> a.getText().length() + " / " + maxTextLength).color(Color.lightGray);
                    
                    Slider endSlider = new Slider(1f, 100f, 5f, false);
                    endSlider.setValue(endFade);
                    Label endLabel = new Label(String.valueOf((int) endFade));

                    Slider startSlider = new Slider(endFade, 100f, 5f, false);
                    startSlider.setValue(startFade);
                    Label startLabel = new Label(String.valueOf((int) startFade));
                    
                    endSlider.changed(() -> {
                        float val = endSlider.getValue();
                        endLabel.setText(String.valueOf((int) val));
                        endFade = val;

                        float clampedEnd = Math.max(startSlider.getValue(), val);
                        startSlider.setRange(val, 100f);
                        startSlider.setValue(clampedEnd);
                        startFade = clampedEnd;
                    });

                    startSlider.changed(() -> {
                        float val = startSlider.getValue();
                        startLabel.setText(String.valueOf((int) val));
                        startFade = val;
                    });

                    dialog.cont.row();
                    dialog.cont.add("Fade End Distance").color(Color.lightGray).left().pad(10f);
                    dialog.cont.row();
                    dialog.cont.table(t -> {
                        t.add(endSlider).width(280f);
                        t.add(endLabel).padLeft(8f).width(30f);
                    });

                    dialog.cont.row();
                    dialog.cont.add("Fade Start Distance").color(Color.lightGray).left().padTop(6f);
                    dialog.cont.row();
                    dialog.cont.table(t -> {
                        t.add(startSlider).width(280f);
                        t.add(startLabel).padLeft(8f).width(30f);
                    });

                    dialog.buttons.button("@ok", () -> {
                        if(!a.getText().contentEquals(message)) configure(a.getText());
                        dialog.hide();
                    }).size(130f, 60f);
                    dialog.update(() -> {
                        if(tile.build != this){
                            dialog.hide();
                        }
                    });
                    dialog.closeOnBack();
                    dialog.show();
                }
                deselect();
            }).size(40f);
        }
    }
}

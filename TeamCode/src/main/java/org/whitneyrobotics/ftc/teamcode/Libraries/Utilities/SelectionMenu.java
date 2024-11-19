package org.whitneyrobotics.ftc.teamcode.Libraries.Utilities;

import java.util.ArrayList;
import java.util.Arrays;

public class SelectionMenu {
    private String name = "Selection Menu";
    private String instructions;
    public ArrayList<Prompt> prompts = new ArrayList<>();
    private Toggler promptSelector;
    private boolean initialized = false;
    private Object[] lastRecordedArray = new Object[]{}; //useful for saving

    public SelectionMenu() {

    }

    public SelectionMenu(String name) {
        this.name = name;
    }

    public SelectionMenu(String name, Prompt... prompts) {
        this.name = name;
        for (Prompt prompt : prompts) {
            this.prompts.add(prompt);
        }
        init();
    }

    public SelectionMenu addPrompt(Prompt prompt) {
        prompts.add(prompt);
        return this;
    }

    public SelectionMenu addInstructions(String instructions){
        this.instructions = instructions;
        return this;
    }

    public SelectionMenu init() {
        promptSelector = new Toggler(this.prompts.size());
        for (Prompt prompt : this.prompts) {
            prompt.init();
        }
        initialized = true;
        return this;
    }

    public void run(boolean incPrompt, boolean decPrompt, boolean incSelection, boolean decSelection) {
        if(initialized) {
            promptSelector.changeState(incPrompt, decPrompt);
            Prompt currentPrompt = prompts.get(promptSelector.currentState());
            currentPrompt.changeActiveSelection(incSelection, decSelection);
        }
    }

    public String formatDisplay(){
        String formatted = "";
        if(initialized){
            formatted += this.name + "\n";
            formatted += (instructions != null) ? instructions + "\n" : "";
            Prompt currentPrompt = prompts.get(promptSelector.currentState());
            formatted += String.format("\n[Prompt %d of %d]: ",promptSelector.currentState()+1,promptSelector.howManyStates()) + currentPrompt.caption + "\n";
            formatted += currentPrompt.getPrintableOutput();
        }
        return formatted;
    }

    /*
    * Autonomous Configuration
    * (Instructions here)
    * [Prompt 1 of 6]: Park State
    * 0: ON <-
    * 1: OFF*/

    public Object[] getOutputs() {
        Object[] outputs = new Object[prompts.size()];
        try{
            for (int i = 0; i < prompts.size(); i++) {
                outputs[i] = prompts.get(i).getValueOfActive();
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            return new Object[] {"sus"};
        }

        return outputs;
    }

    public boolean hasChanged(){
        boolean changed = (!Arrays.equals(lastRecordedArray,getOutputs()));
        lastRecordedArray = getOutputs();
        return changed;
    }

    public ArrayList<Prompt> getPrompts(){return this.prompts;}

    public static class Prompt {
        private String caption;
        private int active;
        private Toggler selectionIterator;
        public ArrayList<Selection> selections = new ArrayList<Selection>();

        public Prompt() {
        }

        public Prompt(String caption) {
            this.caption = caption;
        }

        public Prompt(String caption, Selection... selections) {
            this.caption = caption;
            for (Selection selection : selections) {
                this.selections.add(selection);
            }
            selectionIterator = new Toggler(this.selections.size());
        }

        public Prompt(String caption, ArrayList<Selection> selections) {
            this.caption = caption;
            this.selections = selections;
            selectionIterator = new Toggler(selections.size());
        }

        public Prompt setSelections(Selection[] selections) {
            for (Selection selection : selections) {
                this.selections.add(selection);
            }
            selectionIterator = new Toggler(this.selections.size());
            return this;
        }

        public Prompt setSelections(ArrayList<Selection> selections) {
            this.selections = selections;
            selectionIterator = new Toggler(this.selections.size());
            return this;
        }

        public Prompt addSelection(Selection selection) {
            selections.add(selection);
            return this;
        }

        //If it doesn't accept these primitives, an alternative would be to use the Integer and Double class by passing in primitives, and invoke the Integer/Double constructor in the Selection constructor
        public Prompt addSelection(String caption, double value) {
            selections.add(new Selection(caption, value));
            return this;
        }

        public Prompt addSelection(String name, int value) {
            selections.add(new Selection(name, value));
            return this;
        }

        public Prompt addSelection(String name, boolean value){
            selections.add(new Selection(name, value));
            return this;
        }

        public Prompt addSelection(String name, String value) {
            selections.add(new Selection(name, value));
            return this;
        }

        public Prompt init() {
            selectionIterator = new Toggler(this.selections.size());
            for (Selection selection : this.selections) {
                selection.init();
            }
            return this;
        }

        public ArrayList<Selection> getSelections() {
            return this.selections;
        }

        public String getSelectionsAsString() {
            return this.selections.toString();
        }

        public Prompt changeActiveSelection(boolean next, boolean back) {
            selectionIterator.changeState(next,back);
            active = selectionIterator.currentState();
            return this;
        }

        public String getPrintableOutput() {
            String outputChain = "";
            for (int i = 0; i < this.selections.size(); i++) {
                outputChain += i + ": " + selections.get(i).getCaption();
                outputChain += (i == active) ? " <-\n" : "\n";
            }
            return outputChain;
        }

        public <Value> Value getValueOfActive() {
            return (Value) selections.get(active).getValue();
        }
    }

    public static class Slider extends Prompt {
        private int minInclusive;
        private int maxExclusive;
        private int step = 1;

        public Slider(String caption){
            super(caption);
        }

        public Slider(String caption, int minInclusive, int maxExclusive){
            super(caption);
            this.minInclusive = minInclusive;
            this.maxExclusive = maxExclusive;
            init();
        }

        public Slider setMinInclusive(int minInclusive){this.minInclusive = minInclusive; return this;}
        public Slider setMaxExclusive(int maxExclusive){this.maxExclusive = maxExclusive; return this;}
        public Slider setStep(int step){this.step = step; return this;}
        public Slider setLimits(int min, int max){this.minInclusive = min; this.maxExclusive = max; return this;}

        @Override
        public Slider init() {super.selectionIterator = new Toggler((int)Math.floor((maxExclusive-minInclusive)/step)); return this;}

        @Override
        public String getPrintableOutput(){return "(" + minInclusive + "-" + (maxExclusive-1) + ") " + (minInclusive+super.selectionIterator.currentState()*step) + "\n";}
        //(1-5)
        //Slider("Review your experience",1,6)
        @Override
        public <Value> Value getValueOfActive(){return (Value) new Integer(minInclusive+super.selectionIterator.currentState());}

        //Disabling parent class methods
        @Override
        public Prompt setSelections(ArrayList<Selection> selections){
            throw new UnsupportedOperationException("Disabled");
        }
        @Override
        public Prompt setSelections(Selection[] selections){
            throw new UnsupportedOperationException("Disabled");
        }

        @Override
        public Prompt addSelection(Selection selection) {
            throw new UnsupportedOperationException("Disabled");
        }

        @Override
        //If it doesn't accept these primitives, an alternative would be to use the Integer and Double class by passing in primitives, and invoke the Integer/Double constructor in the Selection constructor
        public Prompt addSelection(String caption, double value) {
            throw new UnsupportedOperationException("Disabled");
        }

        @Override
        public Prompt addSelection(String name, int value) {
            throw new UnsupportedOperationException("Disabled");
        }

        @Override
        public Prompt addSelection(String name, boolean value){
            throw new UnsupportedOperationException("Disabled");
        }

        @Override
        public Prompt addSelection(String name, String value) {
            throw new UnsupportedOperationException("Disabled");
        }

        @Override
        public ArrayList<Selection> getSelections() {
            throw new UnsupportedOperationException("Disabled");
        }

        @Override
        public String getSelectionsAsString() {
            throw new UnsupportedOperationException("Disabled");
        }

    }
    public static class Selection<Value> {
        private String caption = "";
        private Value value;

        public Selection() {
        }

        public Selection(String caption) {
            this.caption = caption;
        }

        public Selection(String caption, Value value) {
            this.caption = caption;
            this.value = value;
        }

        public Selection setCaption(String caption) {
            this.caption = caption;
            return this;
        }

        public Selection setValue(Value value) {
            this.value = value;
            return this;
        }

        public String getCaption() {
            return caption;
        }

        public Value getValue() {
            return value;
        }

        public Selection init() {
            if (caption.length() > 22) {
                caption = caption.substring(0, 22) + "...";
            }
            return this;
        }

    /*public class NumberSelection extends Selection<Double> {
        private String caption;
        private double value;
        public NumberSelection(){super();}
        public NumberSelection(String caption){super(caption);}
        public NumberSelection(String caption, double value){super(caption, value);}
        //@Override
        //public NumberSelection setValue(double value){this.value=value; return this;}
        //@Override
        //public double getValue(){return (double) this.value;}
    }

    public class StringSelection extends Selection {
        private String caption;
        private double value;
    }*/
    }
}

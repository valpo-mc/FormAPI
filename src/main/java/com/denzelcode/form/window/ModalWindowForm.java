package com.denzelcode.form.window;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.window.FormWindow;
import cn.nukkit.form.window.FormWindowModal;
import cn.nukkit.scheduler.ServerScheduler;
import cn.nukkit.scheduler.Task;
import com.denzelcode.form.FormAPI;
import com.denzelcode.form.event.ModalFormSubmitEvent;
import com.denzelcode.form.handler.IHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ModalWindowForm extends FormWindowModal implements IWindowForm<ModalWindowForm, ModalFormSubmitEvent> {

    List<IHandler<ModalFormSubmitEvent>> handlers = new ArrayList<>();

    protected String name = UUID.randomUUID().toString();

    public ModalWindowForm(String name, String title, String content, String acceptButtonText, String cancelButtonText) {
        super(title, content, acceptButtonText, cancelButtonText);

        this.name = name == null ? UUID.randomUUID().toString() : name;
    }

    public ModalWindowForm(String title, String content, String acceptButtonText, String cancelButtonText) {
        super(title, content, acceptButtonText, cancelButtonText);
    }

    public String getAcceptButton() {
        return super.getButton1();
    }

    public String getCancelButton() {
        return super.getButton2();
    }

    public ModalWindowForm setAcceptButton(String button1) {
        super.setButton1(button1);

        return this;
    }

    public ModalWindowForm setCancelButton(String button2) {
        super.setButton2(button2);

        return this;
    }

    @Override
    public boolean wasClosed() {
        return super.wasClosed() || this.getResponse() == null;
    }

    @Override
    public boolean isValid(String formName) {
        return !this.wasClosed() && this.getName().equals(formName);
    }

    @Override
    public ModalWindowForm addHandler(IHandler<ModalFormSubmitEvent> handler) {
        handlers.add(handler);

        return this;
    }

    @Override
    public void clearHandlers() {
        handlers.clear();
    }

    @Override
    public void dispatchHandlers(ModalFormSubmitEvent event) {
        handlers.forEach((handler) -> handler.handle(event));
    }

    @Override
    public List<IHandler<ModalFormSubmitEvent>> getWindowFormHandlers() {
        return handlers;
    }

    @Override
    public void sendTo(Player player) {
        FormAPI.sendWindow(player, this);
    }

    @Override
    public String getName() {
        return name;
    }
}

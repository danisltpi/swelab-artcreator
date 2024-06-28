package artcreator.creator;

import java.awt.image.BufferedImage;
import java.io.File;

import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.statemachine.StateMachineFactory;
import artcreator.statemachine.port.State;
import artcreator.statemachine.port.State.S;
import artcreator.statemachine.port.StateMachine;

public class CreatorFacade implements CreatorFactory, Creator {

	private CreatorImpl creator;
	private StateMachine stateMachine;
	
	@Override
	public Creator creator() {
		if (this.creator == null) {
			this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
			this.creator = new CreatorImpl(stateMachine, DomainFactory.FACTORY.domain());
		}
		return this;
	}

	@Override
	public synchronized void sysop(String str) {
		if (this.stateMachine.getState().isSubStateOf( S.CREATE_TEMPLATE /* choose right state*/ ))
			this.creator.sysop(str);
	}

	@Override
	public void importImage(File file) {
		// TODO Auto-generated method stub
		if (this.stateMachine.getState().isSubStateOf( S.CREATE_TEMPLATE /* choose right state*/ ))
			this.creator.importImage(file);
	}

	@Override
	public void createPreviewWithTemplate(String type) {
		System.out.println(stateMachine.getState());
		if (this.stateMachine.getState().isSubStateOf( S.CHOOSE_TEMPLATE_TYPE /* choose right state*/ ) || (this.stateMachine.getState().isSubStateOf( S.TEMPLATE_CREATED /* choose right state*/ )))
			this.creator.createPreviewWithTemplate(type);
	}

	@Override
	public void createPreviewOfOriginalPicture() {
		System.out.println(stateMachine.getState());
		if (this.stateMachine.getState().isSubStateOf( S.IMPORT_IMAGE /* choose right state*/ ))
			this.creator.createPreviewOfOriginalPicture();
	}

	public BufferedImage getTemplateImage(){
		return this.creator.getTemplateImage();
	}

	public BufferedImage getOriginalImage(){
		return this.creator.getOriginalImage();
	}

	public void saveTemplate(String path){
		this.creator.saveTemplate(path);
	};

	public BufferedImage scaleImage(BufferedImage image, int width, int height){
		return this.creator.scaleImage(image, width, height);
	}

	public void setState(State state) {
		this.creator.setState(state);
	}


}

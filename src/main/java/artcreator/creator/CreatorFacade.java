package artcreator.creator;

import java.awt.image.BufferedImage;
import java.io.File;

import artcreator.creator.impl.CreatorImpl;
import artcreator.creator.port.Creator;
import artcreator.domain.DomainFactory;
import artcreator.statemachine.StateMachineFactory;
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
		// TODO Auto-generated method stub
		this.creator.createPreviewWithTemplate(type);
	}
	public BufferedImage getTemplateImage(){
		return this.creator.getTemplateImage();
	}

	public void saveTemplate(String path){
		this.creator.saveTemplate(path);
	};
	
	

}

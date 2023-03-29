package com.buttch;

import org.springframework.batch.item.ItemProcessor;

public class CommandeItemProcessor implements ItemProcessor<CommandeItem, CommandeItem>{

	@Override
	public CommandeItem process(CommandeItem item) throws Exception {
		return item;
	}

}

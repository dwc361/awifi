package org.jasig.ticket.registry;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.ticket.ServiceTicket;
import org.jasig.cas.ticket.Ticket;
import org.jasig.cas.ticket.TicketGrantingTicket;
import org.jasig.cas.ticket.registry.AbstractDistributedTicketRegistry;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.support.collections.DefaultRedisMap;

public class RedisTicketRegistry extends AbstractDistributedTicketRegistry
		implements InitializingBean {
	private static final String SERVICE_TICKET = "ServiceTicket-";
	private static final String TICKET_GRANTING_TICKET = "TicketGrantingTicket-";
	private DefaultRedisMap<String, Ticket> redisMap;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void addTicket(Ticket ticket) {
		if (ticket instanceof ServiceTicket) {
			redisMap.put(SERVICE_TICKET + ticket.getId(), ticket);
			log.debug("Adding service ticket {} to the cache", ticket.getId());
		} else if (ticket instanceof TicketGrantingTicket) {
			log.debug("Adding ticket granting ticket {} to the cache {}",
					ticket.getId());
			redisMap.put(TICKET_GRANTING_TICKET + ticket.getId(), ticket);
		} else {
			throw new IllegalArgumentException("Invalid ticket type " + ticket);
		}
	}

	@Override
	public Ticket getTicket(String ticketId) {
		Ticket ticket = redisMap.get(SERVICE_TICKET + ticketId);
		if (ticket == null) {
			ticket = redisMap.get(TICKET_GRANTING_TICKET + ticketId);
		}
		return ticket;
	}

	@Override
	public boolean deleteTicket(String ticketId) {
		if (StringUtils.isBlank(ticketId)) {
			return false;
		}
		if (redisMap.remove(SERVICE_TICKET + ticketId) == null
				&& redisMap.remove(TICKET_GRANTING_TICKET + ticketId) == null) {
			return false;
		}
		return true;
	}

	@Override
	public Collection<Ticket> getTickets() {
		return redisMap.values();
	}

	@Override
	protected void updateTicket(Ticket ticket) {
		addTicket(ticket);
	}

	@Override
	protected boolean needsCallback() {
		return false;
	}

	public DefaultRedisMap<String, Ticket> getRedisMap() {
		return redisMap;
	}

	public void setRedisMap(DefaultRedisMap<String, Ticket> redisMap) {
		this.redisMap = redisMap;
	}

}

package ru.nikita.QuickOrderBot;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.nikita.QuickOrderBot.entity.Order;
import ru.nikita.QuickOrderBot.entity.OrderItem;
import ru.nikita.QuickOrderBot.entity.User;
import ru.nikita.QuickOrderBot.enums.OrderStatus;
import ru.nikita.QuickOrderBot.repository.OrderItemRepository;
import ru.nikita.QuickOrderBot.repository.OrderRepository;
import ru.nikita.QuickOrderBot.repository.UserRepository;
import ru.nikita.QuickOrderBot.repository.criteria.OrderRepositoryImpl;
import ru.nikita.QuickOrderBot.repository.criteria.UserRepositoryImpl;
import ru.nikita.QuickOrderBot.services.TransactionalOrderService;
import ru.nikita.QuickOrderBot.services.impl.TransactionalOrderServiceImpl;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class QuickOrderBotApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderRepositoryImpl orderRepositoryImpl;

	@Autowired
	private UserRepositoryImpl userRepositoryImpl;

	@Autowired
	private TransactionalOrderServiceImpl transactionalOrderService;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Test
	void testFindOrdersByUserEmail_queryMethod() {
		User user = new User();
		user.setEmail("test@example.com");
		userRepository.save(user);

		Order order = new Order();
		order.setUser(user);
		orderRepository.save(order);

		List<Order> orders = orderRepository.findOrdersByUserEmail("test@example.com");
		assertEquals(1, orders.size());
		assertEquals(order.getId(), orders.getFirst().getId());
	}

	@Test
	void testFindOrdersByUserEmail_criteriaImpl() {
		User user = new User();
		user.setEmail("impl@example.com");
		userRepository.save(user);

		Order order = new Order();
		order.setUser(user);
		orderRepository.save(order);

		List<Order> orders = orderRepositoryImpl.findOrdersByUserEmail("impl@example.com");
		assertEquals(1, orders.size());
		assertEquals(order.getId(), orders.getFirst().getId());
	}

	@Test
	void testFindByFirstNameAndLastName_queryMethod() {
		User user = new User();
		user.setFirstName("Alice");
		user.setLastName("Smith");
		userRepository.save(user);

		List<User> users = userRepository.findByFirstNameAndLastName("Alice", "Smith");
		assertEquals(1, users.size());
		assertEquals(user.getId(), users.getFirst().getId());
	}

	@Test
	void testFindByFirstNameAndLastName_criteriaImpl() {
		User user = new User();
		user.setFirstName("Bob");
		user.setLastName("Johnson");
		userRepository.save(user);

		List<User> users = userRepositoryImpl.findByFirstNameAndLastName("Bob", "Johnson");
		assertEquals(1, users.size());
		assertEquals(user.getId(), users.getFirst().getId());
	}

	@Test
	void testCreateOrderWithItems_success() {
		Order order = new Order();
		order.setStatus(OrderStatus.CREATED);
		OrderItem item1 = new OrderItem();
		OrderItem item2 = new OrderItem();
		List<OrderItem> items = Arrays.asList(item1, item2);

		Order savedOrder = transactionalOrderService.createOrderWithItems(order, items);

		assertNotNull(savedOrder.getId());
		List<OrderItem> itemsInDB = orderItemRepository.findByOrderId(savedOrder.getId());
		assertEquals(2, itemsInDB.size());
	}

	@Test
	void testCreateOrderWithItems_rollbackOnException() {
		Order order = new Order();
		order.setStatus(OrderStatus.CREATED);
		OrderItem item1 = new OrderItem();
		List<OrderItem> items = Arrays.asList(item1, null); // вызовет NullPointerException в saveAll

		assertThrows(NullPointerException.class, () -> {
			transactionalOrderService.createOrderWithItems(order, items);
		});

		List<Order> orders = orderRepository.findAll();
		assertTrue(orders.isEmpty());

		List<OrderItem> itemsInDB = orderItemRepository.findAll();
		assertTrue(itemsInDB.isEmpty());
	}
}

package com.arthurprojects.pezesha.service.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arthurprojects.pezesha.dto.AccountDTO;
import com.arthurprojects.pezesha.entity.Account;
import com.arthurprojects.pezesha.exception.ResourceNotFoundException;
import com.arthurprojects.pezesha.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AccountServiceImplDiffblueTest {
    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    /**
     * Test {@link AccountServiceImpl#createAccount(AccountDTO)}.
     * <ul>
     *   <li>Given {@link Account#Account()} AccountName is {@code Dr Jane Doe}.</li>
     *   <li>Then return {@link Account#Account()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#createAccount(AccountDTO)}
     */
    @Test
    @DisplayName("Test createAccount(AccountDTO); given Account() AccountName is 'Dr Jane Doe'; then return Account()")
    void testCreateAccount_givenAccountAccountNameIsDrJaneDoe_thenReturnAccount() {
        // Arrange
        Account account = new Account();
        account.setAccountName("Dr Jane Doe");
        account.setBalance(new BigDecimal("2.3"));
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setVersion(1L);
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account);

        // Act
        Account actualCreateAccountResult = accountServiceImpl
                .createAccount(new AccountDTO(1L, "Dr Jane Doe", new BigDecimal("2.3"), 1L));

        // Assert
        verify(accountRepository).save(isA(Account.class));
        assertSame(account, actualCreateAccountResult);
    }

    /**
     * Test {@link AccountServiceImpl#createAccount(AccountDTO)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#createAccount(AccountDTO)}
     */
    @Test
    @DisplayName("Test createAccount(AccountDTO); then throw ResourceNotFoundException")
    void testCreateAccount_thenThrowResourceNotFoundException() {
        // Arrange
        when(accountRepository.save(Mockito.<Account>any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> accountServiceImpl.createAccount(new AccountDTO(1L, "Dr Jane Doe", new BigDecimal("2.3"), 1L)));
        verify(accountRepository).save(isA(Account.class));
    }

    /**
     * Test {@link AccountServiceImpl#createAccount(AccountDTO)}.
     * <ul>
     *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code -2.3}.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#createAccount(AccountDTO)}
     */
    @Test
    @DisplayName("Test createAccount(AccountDTO); when BigDecimal(String) with '-2.3'; then throw IllegalArgumentException")
    void testCreateAccount_whenBigDecimalWith23_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class,
                () -> accountServiceImpl.createAccount(new AccountDTO(1L, "Dr Jane Doe", new BigDecimal("-2.3"), 1L)));
    }

    /**
     * Test {@link AccountServiceImpl#getAccountById(Long)}.
     * <p>
     * Method under test: {@link AccountServiceImpl#getAccountById(Long)}
     */
    @Test
    @DisplayName("Test getAccountById(Long)")
    void testGetAccountById() {
        // Arrange
        when(accountRepository.findById(Mockito.<Long>any())).thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.getAccountById(1L));
        verify(accountRepository).findById(eq(1L));
    }

    /**
     * Test {@link AccountServiceImpl#getAccountById(Long)}.
     * <ul>
     *   <li>Given {@link Account#Account()} AccountName is {@code Dr Jane Doe}.</li>
     *   <li>Then return {@link Account#Account()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#getAccountById(Long)}
     */
    @Test
    @DisplayName("Test getAccountById(Long); given Account() AccountName is 'Dr Jane Doe'; then return Account()")
    void testGetAccountById_givenAccountAccountNameIsDrJaneDoe_thenReturnAccount() {
        // Arrange
        Account account = new Account();
        account.setAccountName("Dr Jane Doe");
        account.setBalance(new BigDecimal("2.3"));
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setVersion(1L);
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Account actualAccountById = accountServiceImpl.getAccountById(1L);

        // Assert
        verify(accountRepository).findById(eq(1L));
        assertSame(account, actualAccountById);
    }

    /**
     * Test {@link AccountServiceImpl#getAccountById(Long)}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#getAccountById(Long)}
     */
    @Test
    @DisplayName("Test getAccountById(Long); given AccountRepository findById(Object) return empty")
    void testGetAccountById_givenAccountRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.getAccountById(1L));
        verify(accountRepository).findById(eq(1L));
    }

    /**
     * Test {@link AccountServiceImpl#getAllAccounts()}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link ListCrudRepository#findAll()}
     * return {@code null}.</li>
     *   <li>Then return {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#getAllAccounts()}
     */
    @Test
    @DisplayName("Test getAllAccounts(); given AccountRepository findAll() return 'null'; then return 'null'")
    void testGetAllAccounts_givenAccountRepositoryFindAllReturnNull_thenReturnNull() {
        // Arrange
        when(accountRepository.findAll()).thenReturn(null);

        // Act
        List<Account> actualAllAccounts = accountServiceImpl.getAllAccounts();

        // Assert
        verify(accountRepository).findAll();
        assertNull(actualAllAccounts);
    }

    /**
     * Test {@link AccountServiceImpl#getAllAccounts()}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#getAllAccounts()}
     */
    @Test
    @DisplayName("Test getAllAccounts(); then throw ResourceNotFoundException")
    void testGetAllAccounts_thenThrowResourceNotFoundException() {
        // Arrange
        when(accountRepository.findAll()).thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.getAllAccounts());
        verify(accountRepository).findAll();
    }

    /**
     * Test {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}.
     * <p>
     * Method under test: {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}
     */
    @Test
    @DisplayName("Test updateBalance(Long, BigDecimal)")
    void testUpdateBalance() {
        // Arrange
        Account account = new Account();
        account.setAccountName("Dr Jane Doe");
        account.setBalance(new BigDecimal("2.3"));
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setVersion(1L);
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.save(Mockito.<Account>any())).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.updateBalance(1L, new BigDecimal("2.3")));
        verify(accountRepository).findById(eq(1L));
        verify(accountRepository).save(isA(Account.class));
    }

    /**
     * Test {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link CrudRepository#findById(Object)}
     * return empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}
     */
    @Test
    @DisplayName("Test updateBalance(Long, BigDecimal); given AccountRepository findById(Object) return empty")
    void testUpdateBalance_givenAccountRepositoryFindByIdReturnEmpty() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.updateBalance(1L, new BigDecimal("2.3")));
        verify(accountRepository).findById(eq(1L));
    }

    /**
     * Test {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link CrudRepository#save(Object)}
     * return {@link Account#Account()}.</li>
     *   <li>Then return {@link Account#Account()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}
     */
    @Test
    @DisplayName("Test updateBalance(Long, BigDecimal); given AccountRepository save(Object) return Account(); then return Account()")
    void testUpdateBalance_givenAccountRepositorySaveReturnAccount_thenReturnAccount() {
        // Arrange
        Account account = new Account();
        account.setAccountName("Dr Jane Doe");
        account.setBalance(new BigDecimal("2.3"));
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setVersion(1L);
        Optional<Account> ofResult = Optional.of(account);

        Account account2 = new Account();
        account2.setAccountName("Dr Jane Doe");
        account2.setBalance(new BigDecimal("2.3"));
        account2.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setId(1L);
        account2.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account2.setVersion(1L);
        when(accountRepository.save(Mockito.<Account>any())).thenReturn(account2);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        Account actualUpdateBalanceResult = accountServiceImpl.updateBalance(1L, new BigDecimal("2.3"));

        // Assert
        verify(accountRepository).findById(eq(1L));
        verify(accountRepository).save(isA(Account.class));
        assertSame(account2, actualUpdateBalanceResult);
    }

    /**
     * Test {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}.
     * <ul>
     *   <li>When {@link BigDecimal#BigDecimal(String)} with {@code -2.3}.</li>
     *   <li>Then throw {@link IllegalArgumentException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#updateBalance(Long, BigDecimal)}
     */
    @Test
    @DisplayName("Test updateBalance(Long, BigDecimal); when BigDecimal(String) with '-2.3'; then throw IllegalArgumentException")
    void testUpdateBalance_whenBigDecimalWith23_thenThrowIllegalArgumentException() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> accountServiceImpl.updateBalance(1L, new BigDecimal("-2.3")));
    }

    /**
     * Test {@link AccountServiceImpl#deleteAccount(Long)}.
     * <p>
     * Method under test: {@link AccountServiceImpl#deleteAccount(Long)}
     */
    @Test
    @DisplayName("Test deleteAccount(Long)")
    void testDeleteAccount() {
        // Arrange
        doThrow(new ResourceNotFoundException("An error occurred")).when(accountRepository).deleteById(Mockito.<Long>any());
        when(accountRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.deleteAccount(1L));
        verify(accountRepository).deleteById(eq(1L));
        verify(accountRepository).existsById(eq(1L));
    }

    /**
     * Test {@link AccountServiceImpl#deleteAccount(Long)}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link CrudRepository#deleteById(Object)}
     * does nothing.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#deleteAccount(Long)}
     */
    @Test
    @DisplayName("Test deleteAccount(Long); given AccountRepository deleteById(Object) does nothing")
    void testDeleteAccount_givenAccountRepositoryDeleteByIdDoesNothing() {
        // Arrange
        doNothing().when(accountRepository).deleteById(Mockito.<Long>any());
        when(accountRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        accountServiceImpl.deleteAccount(1L);

        // Assert
        verify(accountRepository).deleteById(eq(1L));
        verify(accountRepository).existsById(eq(1L));
    }

    /**
     * Test {@link AccountServiceImpl#deleteAccount(Long)}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link CrudRepository#existsById(Object)}
     * return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#deleteAccount(Long)}
     */
    @Test
    @DisplayName("Test deleteAccount(Long); given AccountRepository existsById(Object) return 'false'")
    void testDeleteAccount_givenAccountRepositoryExistsByIdReturnFalse() {
        // Arrange
        when(accountRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.deleteAccount(1L));
        verify(accountRepository).existsById(eq(1L));
    }

    /**
     * Test {@link AccountServiceImpl#accountExists(Long)}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link CrudRepository#existsById(Object)}
     * return {@code false}.</li>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#accountExists(Long)}
     */
    @Test
    @DisplayName("Test accountExists(Long); given AccountRepository existsById(Object) return 'false'; then return 'false'")
    void testAccountExists_givenAccountRepositoryExistsByIdReturnFalse_thenReturnFalse() {
        // Arrange
        when(accountRepository.existsById(Mockito.<Long>any())).thenReturn(false);

        // Act
        boolean actualAccountExistsResult = accountServiceImpl.accountExists(1L);

        // Assert
        verify(accountRepository).existsById(eq(1L));
        assertFalse(actualAccountExistsResult);
    }

    /**
     * Test {@link AccountServiceImpl#accountExists(Long)}.
     * <ul>
     *   <li>Given {@link AccountRepository} {@link CrudRepository#existsById(Object)}
     * return {@code true}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#accountExists(Long)}
     */
    @Test
    @DisplayName("Test accountExists(Long); given AccountRepository existsById(Object) return 'true'; then return 'true'")
    void testAccountExists_givenAccountRepositoryExistsByIdReturnTrue_thenReturnTrue() {
        // Arrange
        when(accountRepository.existsById(Mockito.<Long>any())).thenReturn(true);

        // Act
        boolean actualAccountExistsResult = accountServiceImpl.accountExists(1L);

        // Assert
        verify(accountRepository).existsById(eq(1L));
        assertTrue(actualAccountExistsResult);
    }

    /**
     * Test {@link AccountServiceImpl#accountExists(Long)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link AccountServiceImpl#accountExists(Long)}
     */
    @Test
    @DisplayName("Test accountExists(Long); then throw ResourceNotFoundException")
    void testAccountExists_thenThrowResourceNotFoundException() {
        // Arrange
        when(accountRepository.existsById(Mockito.<Long>any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> accountServiceImpl.accountExists(1L));
        verify(accountRepository).existsById(eq(1L));
    }

    /**
     * Test {@link AccountServiceImpl#hasSufficientBalance(Long, BigDecimal)}.
     * <ul>
     *   <li>Given {@link Account#Account()} AccountName is {@code Dr Jane Doe}.</li>
     *   <li>Then return {@code true}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountServiceImpl#hasSufficientBalance(Long, BigDecimal)}
     */
    @Test
    @DisplayName("Test hasSufficientBalance(Long, BigDecimal); given Account() AccountName is 'Dr Jane Doe'; then return 'true'")
    void testHasSufficientBalance_givenAccountAccountNameIsDrJaneDoe_thenReturnTrue() {
        // Arrange
        Account account = new Account();
        account.setAccountName("Dr Jane Doe");
        account.setBalance(new BigDecimal("2.3"));
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setVersion(1L);
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualHasSufficientBalanceResult = accountServiceImpl.hasSufficientBalance(1L, new BigDecimal("2.3"));

        // Assert
        verify(accountRepository).findById(eq(1L));
        assertTrue(actualHasSufficientBalanceResult);
    }

    /**
     * Test {@link AccountServiceImpl#hasSufficientBalance(Long, BigDecimal)}.
     * <ul>
     *   <li>Then return {@code false}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountServiceImpl#hasSufficientBalance(Long, BigDecimal)}
     */
    @Test
    @DisplayName("Test hasSufficientBalance(Long, BigDecimal); then return 'false'")
    void testHasSufficientBalance_thenReturnFalse() {
        // Arrange
        Account account = mock(Account.class);
        when(account.getBalance()).thenReturn(new BigDecimal("-2.3"));
        doNothing().when(account).setAccountName(Mockito.<String>any());
        doNothing().when(account).setBalance(Mockito.<BigDecimal>any());
        doNothing().when(account).setCreatedAt(Mockito.<LocalDateTime>any());
        doNothing().when(account).setId(Mockito.<Long>any());
        doNothing().when(account).setUpdatedAt(Mockito.<LocalDateTime>any());
        doNothing().when(account).setVersion(Mockito.<Long>any());
        account.setAccountName("Dr Jane Doe");
        account.setBalance(new BigDecimal("2.3"));
        account.setCreatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setId(1L);
        account.setUpdatedAt(LocalDate.of(1970, 1, 1).atStartOfDay());
        account.setVersion(1L);
        Optional<Account> ofResult = Optional.of(account);
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        // Act
        boolean actualHasSufficientBalanceResult = accountServiceImpl.hasSufficientBalance(1L, new BigDecimal("2.3"));

        // Assert
        verify(account).getBalance();
        verify(account).setAccountName(eq("Dr Jane Doe"));
        verify(account).setBalance(isA(BigDecimal.class));
        verify(account).setCreatedAt(isA(LocalDateTime.class));
        verify(account).setId(eq(1L));
        verify(account).setUpdatedAt(isA(LocalDateTime.class));
        verify(account).setVersion(eq(1L));
        verify(accountRepository).findById(eq(1L));
        assertFalse(actualHasSufficientBalanceResult);
    }

    /**
     * Test {@link AccountServiceImpl#hasSufficientBalance(Long, BigDecimal)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test:
     * {@link AccountServiceImpl#hasSufficientBalance(Long, BigDecimal)}
     */
    @Test
    @DisplayName("Test hasSufficientBalance(Long, BigDecimal); then throw ResourceNotFoundException")
    void testHasSufficientBalance_thenThrowResourceNotFoundException() {
        // Arrange
        Optional<Account> emptyResult = Optional.empty();
        when(accountRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class,
                () -> accountServiceImpl.hasSufficientBalance(1L, new BigDecimal("2.3")));
        verify(accountRepository).findById(eq(1L));
    }
}

package com.arthurprojects.pezesha.service.impl;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.arthurprojects.pezesha.dto.TransferDTO;
import com.arthurprojects.pezesha.entity.Transfer;
import com.arthurprojects.pezesha.exception.InsufficientBalanceException;
import com.arthurprojects.pezesha.exception.ResourceNotFoundException;
import com.arthurprojects.pezesha.repository.TransferRepository;
import com.arthurprojects.pezesha.service.AccountService;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransferServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TransferServiceImplDiffblueTest {
    @MockBean
    private AccountService accountService;

    @MockBean
    private TransferRepository transferRepository;

    @Autowired
    private TransferServiceImpl transferServiceImpl;

    /**
     * Test {@link TransferServiceImpl#createTransfer(TransferDTO)}.
     * <ul>
     *   <li>Given {@link BigDecimal#BigDecimal(String)} with {@code -2.3}.</li>
     * </ul>
     * <p>
     * Method under test: {@link TransferServiceImpl#createTransfer(TransferDTO)}
     */
    @Test
    @DisplayName("Test createTransfer(TransferDTO); given BigDecimal(String) with '-2.3'")
    void testCreateTransfer_givenBigDecimalWith23() {
        // Arrange
        TransferDTO transferDTO = mock(TransferDTO.class);
        when(transferDTO.getAmount()).thenReturn(new BigDecimal("-2.3"));
        doNothing().when(transferDTO).setAmount(Mockito.<BigDecimal>any());
        doNothing().when(transferDTO).setDescription(Mockito.<String>any());
        doNothing().when(transferDTO).setDestinationAccountId(Mockito.<Long>any());
        doNothing().when(transferDTO).setSourceAccountId(Mockito.<Long>any());
        transferDTO.setAmount(new BigDecimal("2.3"));
        transferDTO.setDescription("The characteristics of someone or something");
        transferDTO.setDestinationAccountId(1L);
        transferDTO.setSourceAccountId(1L);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> transferServiceImpl.createTransfer(transferDTO));
        verify(transferDTO).getAmount();
        verify(transferDTO).setAmount(isA(BigDecimal.class));
        verify(transferDTO).setDescription(eq("The characteristics of someone or something"));
        verify(transferDTO).setDestinationAccountId(eq(1L));
        verify(transferDTO).setSourceAccountId(eq(1L));
    }

    /**
     * Test {@link TransferServiceImpl#createTransfer(TransferDTO)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link TransferServiceImpl#createTransfer(TransferDTO)}
     */
    @Test
    @DisplayName("Test createTransfer(TransferDTO); then throw ResourceNotFoundException")
    void testCreateTransfer_thenThrowResourceNotFoundException() {
        // Arrange
        TransferDTO transferDTO = mock(TransferDTO.class);
        when(transferDTO.getSourceAccountId()).thenThrow(new ResourceNotFoundException("An error occurred"));
        when(transferDTO.getAmount()).thenReturn(new BigDecimal("2.3"));
        doNothing().when(transferDTO).setAmount(Mockito.<BigDecimal>any());
        doNothing().when(transferDTO).setDescription(Mockito.<String>any());
        doNothing().when(transferDTO).setDestinationAccountId(Mockito.<Long>any());
        doNothing().when(transferDTO).setSourceAccountId(Mockito.<Long>any());
        transferDTO.setAmount(new BigDecimal("2.3"));
        transferDTO.setDescription("The characteristics of someone or something");
        transferDTO.setDestinationAccountId(1L);
        transferDTO.setSourceAccountId(1L);

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> transferServiceImpl.createTransfer(transferDTO));
        verify(transferDTO).getAmount();
        verify(transferDTO).getSourceAccountId();
        verify(transferDTO).setAmount(isA(BigDecimal.class));
        verify(transferDTO).setDescription(eq("The characteristics of someone or something"));
        verify(transferDTO).setDestinationAccountId(eq(1L));
        verify(transferDTO).setSourceAccountId(eq(1L));
    }

    /**
     * Test {@link TransferServiceImpl#createTransfer(TransferDTO)}.
     * <ul>
     *   <li>When {@link TransferDTO} (default constructor) Amount is
     * {@link BigDecimal#BigDecimal(String)} with {@code 2.3}.</li>
     * </ul>
     * <p>
     * Method under test: {@link TransferServiceImpl#createTransfer(TransferDTO)}
     */
    @Test
    @DisplayName("Test createTransfer(TransferDTO); when TransferDTO (default constructor) Amount is BigDecimal(String) with '2.3'")
    void testCreateTransfer_whenTransferDTOAmountIsBigDecimalWith23() {
        // Arrange
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setAmount(new BigDecimal("2.3"));
        transferDTO.setDescription("The characteristics of someone or something");
        transferDTO.setDestinationAccountId(1L);
        transferDTO.setSourceAccountId(1L);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> transferServiceImpl.createTransfer(transferDTO));
    }

    /**
     * Test {@link TransferServiceImpl#getAllTransfers()}.
     * <ul>
     *   <li>Given {@link TransferRepository} {@link ListCrudRepository#findAll()}
     * return {@code null}.</li>
     *   <li>Then return {@code null}.</li>
     * </ul>
     * <p>
     * Method under test: {@link TransferServiceImpl#getAllTransfers()}
     */
    @Test
    @DisplayName("Test getAllTransfers(); given TransferRepository findAll() return 'null'; then return 'null'")
    void testGetAllTransfers_givenTransferRepositoryFindAllReturnNull_thenReturnNull() {
        // Arrange
        when(transferRepository.findAll()).thenReturn(null);

        // Act
        List<Transfer> actualAllTransfers = transferServiceImpl.getAllTransfers();

        // Assert
        verify(transferRepository).findAll();
        assertNull(actualAllTransfers);
    }

    /**
     * Test {@link TransferServiceImpl#getAllTransfers()}.
     * <ul>
     *   <li>Then throw {@link InsufficientBalanceException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link TransferServiceImpl#getAllTransfers()}
     */
    @Test
    @DisplayName("Test getAllTransfers(); then throw InsufficientBalanceException")
    void testGetAllTransfers_thenThrowInsufficientBalanceException() {
        // Arrange
        when(transferRepository.findAll()).thenThrow(new InsufficientBalanceException("An error occurred"));

        // Act and Assert
        assertThrows(InsufficientBalanceException.class, () -> transferServiceImpl.getAllTransfers());
        verify(transferRepository).findAll();
    }
}

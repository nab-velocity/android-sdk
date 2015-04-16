/**
 * 
 */
package com.velocity.enums;

/**
 * @author ranjitk
 *
 */
public enum TransactionState {

	Adjusted,
	Authorized,
	Captured,
	CaptureDeclined,
	Declined,
	ErrorConnecting,
	ErrorUnknown,
	ErrorValidation,
	FundsRequested,
	FundsTransferred,
	InProcess,
	NotSet,
	PartiallyCaptured,
	PartiallyReturned,
	PartialReturnRequested,
	Returned,
	ReturnRequested,
	ReturnUndone,
	Undone,
	Verified
}

/**
 * 
 */
package com.velocity.enums;

/**
 * @author ranjitk
 *
 */
public enum CaptureState {

	BatchSent,
	BatchSentUndoPermitted,
	CannotCapture,
	Captured,
	CaptureDeclined,
	CapturedUndoPermitted,
	CaptureError,
	CapturePending,
	CapturePendingUndoPermitted,
	CaptureUnknown,
	InProcess,
	NotSet,
	ReadyForCapture,
	UndoReported
}

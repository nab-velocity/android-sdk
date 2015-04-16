/**
 * 
 */
package com.velocity.model.captureAll.request;

/**
 * @author ranjitk
 *
 */
public class CaptureAllTransaction {
	
	private BatchIds batchIds;
	
	private DifferenceData  differenceData;

	public BatchIds getBatchIds() {
		if(batchIds == null){
			
			batchIds = new BatchIds();
		}
		return batchIds;
	}

	public void setBatchIds(BatchIds batchIds) {
		this.batchIds = batchIds;
	}

	public DifferenceData getDifferenceData() {
		
		if(differenceData == null){
			
			differenceData = new DifferenceData();
		}
		return differenceData;
	}

	public void setDifferenceData(DifferenceData differenceData) {
		this.differenceData = differenceData;
	}
	
	

}

package com.git.qaproducer.preset.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.git.qaproducer.preset.domain.Preset;
import com.git.qaproducer.preset.mapper.PresetMapper;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;

@Repository("presetRepository")
public class PresetRepository extends EgovAbstractMapper{

	@Resource(name = "presetMapper")
	private PresetMapper presetMapper;

	public Preset retrieveBasePreset(int cat) {
		return presetMapper.retrieveBasePreset(cat);
	}

	public Preset retrievePridByBasePreset(int cat) {
		return presetMapper.retrievePridByBasePreset(cat);
	}

	public Preset retrievePresetById(int pid) {
		return presetMapper.retrievePresetById(pid);
	}

	public Preset retrieveCatByPreset(int pid) {
		return presetMapper.retrieveCatByPreset(pid);
	}

	public List<Preset> retrievePresetByUidx(int uidx) {
		return presetMapper.retrievePresetByUidx(uidx);
	}

	public List<Preset> retrievePresetNamesByUidx(int uidx) {
		return presetMapper.retrievePresetNamesByUidx(uidx);
	}

	public void createPreset(Preset preset) {
		presetMapper.createPreset(preset);
	}

	public boolean updatePreset(Preset preset) {
		boolean flag = false;
		int num = presetMapper.updatePreset(preset);
		if (num > 0) {
			flag = true;
		}
		return flag;
	}

	public boolean deletePresets(ArrayList<Preset> prList) {
		boolean flag = false;
		int num = presetMapper.deletePresets(prList);
		if (num > 0) {
			flag = true;
		}
		return flag;
	}

	public Preset retrievePresetByIdAndUidx(Preset preset) {
		return presetMapper.retrievePresetByIdAndUidx(preset);
	}

	public List<Preset> retrievePresetNamesByNameAndUidx(Preset preset) {
		return presetMapper.retrievePresetNamesByNameAndUidx(preset);
	}

	public List<Preset> retrievePresetNamesByNameAndUidxAndPid(Preset preset) {
		return presetMapper.retrievePresetNamesByNameAndUidxAndPid(preset);
	}

}

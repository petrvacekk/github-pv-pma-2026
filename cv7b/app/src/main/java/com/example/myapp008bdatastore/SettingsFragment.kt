package com.example.myapp008bdatastore

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myapp008bdatastore.databinding.FragmentSettingsBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

val Context.dataStore by preferencesDataStore(name = "gamer_profile")

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val NICKNAME_KEY = stringPreferencesKey("NICKNAME")
    private val HARDCORE_KEY = booleanPreferencesKey("HARDCORE")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Načtení uložených dat
        viewLifecycleOwner.lifecycleScope.launch {
            val preferences = requireContext().dataStore.data.first()
            binding.etNickname.setText(preferences[NICKNAME_KEY] ?: "")
            binding.switchHardcoreMode.isChecked =
                preferences[HARDCORE_KEY] ?: false
        }

        // NOVINKA: Interaktivní posuvník
        // Funguje nezávisle na ukládání dat
        binding.seekBarBrightness.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    binding.tvBrightness.text =
                        "Jas herního prostředí: $progress %"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            }
        )

        // Uložení dat
        binding.btnSave.setOnClickListener {

            val currentName = binding.etNickname.text.toString()
            val currentHardcore = binding.switchHardcoreMode.isChecked

            viewLifecycleOwner.lifecycleScope.launch {

                // Zobrazení načítacího kolečka
                binding.progressBar.visibility = View.VISIBLE
                binding.btnSave.isEnabled = false

                // Simulace třísekundového ukládání
                // delay() neblokuje hlavní vlákno
                delay(3000)

                // Uložení do DataStore
                requireContext().dataStore.edit { prefs ->
                    prefs[NICKNAME_KEY] = currentName
                    prefs[HARDCORE_KEY] = currentHardcore
                }

                // Dokončení ukládání
                binding.progressBar.visibility = View.INVISIBLE
                binding.btnSave.isEnabled = true

                Snackbar.make(
                    binding.root,
                    "Uloženo moderní cestou",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Zpět") {
                        Toast.makeText(
                            requireContext(),
                            "Akce vrácena zpět!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

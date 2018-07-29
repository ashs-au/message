//minim library audio code: a simple oscillator
class Buzzer implements Instrument {
  Oscil sineOsc;
  Multiplier multiplyGate;
  AudioOutput aout;
 
  Buzzer(float frequency, float amplitude, AudioOutput out ) {
    aout = out;
    sineOsc = new Oscil(frequency, amplitude);
    multiplyGate = new Multiplier(0);
 
    sineOsc.patch( multiplyGate );
  }
 
  void noteOn( float dur ) {
    multiplyGate.setValue(0.4);
    multiplyGate.patch(aout);
  }
 
  void noteOff() {
    multiplyGate.setValue(0.0);
    multiplyGate.unpatch(aout);
  }
}

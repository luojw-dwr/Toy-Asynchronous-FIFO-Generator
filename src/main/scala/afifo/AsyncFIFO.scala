package afifo

import chisel3._
import chisel3.util._

import chisel3.experimental.chiselName

class AsyncFIFO[T <: Data](private val gen: T, val suggestDepth: Int, val nPtrSyncR2W: Int = 2, val nPtrSyncW2R: Int = 2) extends RawModule {

    val addrWidth = log2Up(suggestDepth)
    val depth     = 1 << addrWidth

    val wclk = IO(Input(Clock()))
    val wrst = IO(Input(AsyncReset()))
    val rclk = IO(Input(Clock()))
    val rrst = IO(Input(AsyncReset()))
    val w    = IO(Flipped(new FIFOWritePortIO(gen)))
    val r    = IO(Flipped(new FIFOReadPortIO(gen)))
    
    val wctrl = withClockAndReset(wclk, wrst) { Module(new WCtrl(addrWidth, nPtrSyncR2W)).io }
    val rctrl = withClockAndReset(rclk, rrst) { Module(new RCtrl(addrWidth, nPtrSyncW2R)).io }
    val mem   = Module(new S2P(gen.cloneType, depth))
    
    wctrl.ptrs <> rctrl.ptrs
    wctrl.mem_wctrl <> mem.w.wctrl
    rctrl.mem_rctrl <> mem.r.rctrl
    
    wctrl.winc := w.winc
    rctrl.rinc := r.rinc
    
    w.wfull  := wctrl.wfull
    r.rempty := rctrl.rempty
    
    mem.w.wbits := w.wbits
    r.rbits := mem.r.rbits
    
}

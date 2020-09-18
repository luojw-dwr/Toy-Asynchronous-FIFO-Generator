package afifo

import chisel3._
import chisel3.util._

import chisel3.experimental.chiselName

@chiselName
class WCtrl(val addrWidth: Int, val nSync: Int = 2) extends Module {

    require(addrWidth > 0)
    val ptrWidth = addrWidth + 1
    
    val io = IO(new Bundle {
        val ptrs      = new CtrlPtrIO(ptrWidth)
        val mem_wctrl = new MemWritePortCtrlIO(addrWidth)
        val winc      = Input(Bool())
        val wfull     = Output(Bool())
    })
    
    val we = io.winc & (~io.wfull)
    
    val rptr_sync = SyncFF(io.ptrs.rptr, nSync, "rptr_sync")
    
    // > {wptr, waddr} generation
    
    val wbin_reg = RegInit(0.U(ptrWidth.W))
    val wbin_next = wbin_reg + we
    wbin_reg := wbin_next
    
    val wptr_reg = RegInit(0.U(ptrWidth.W))
    val wptr_next = bin2gray(wbin_next)
    wptr_reg := wptr_next
    
    val waddr = wbin_reg(addrWidth - 1, 0)
    
    // < {wptr, waddr} generation
    
    // > wfull generation
    
    val wfull_reg = RegNext(fullInGray(wptr_next, rptr_sync), init=false.B)
    
    // < wfull generation
    
    io.ptrs.wptr       := wptr_reg
    io.mem_wctrl.wclk  := clock
    io.mem_wctrl.we    := we
    io.mem_wctrl.waddr := waddr
    io.wfull           := wfull_reg
    
}

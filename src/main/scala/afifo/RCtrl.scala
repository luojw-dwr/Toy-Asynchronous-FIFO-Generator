package afifo

import chisel3._
import chisel3.util._

import chisel3.experimental.chiselName

@chiselName
class RCtrl(val addrWidth: Int, val nSync: Int = 2) extends Module {

    require(addrWidth > 0)
    val ptrWidth = addrWidth + 1
    
    val io = IO(new Bundle {
        val ptrs      = Flipped(new CtrlPtrIO(ptrWidth))
        val mem_rctrl = new MemReadPortCtrlIO(addrWidth)
        val rinc      = Input(Bool())
        val rempty    = Output(Bool())
    })
    
    val wptr_sync = SyncFF(io.ptrs.wptr, nSync, "wptr_sync")
    
    // > {rptr, raddr} generation
    
    val rbin_reg = RegInit(0.U(ptrWidth.W))
    val rbin_next = rbin_reg + (io.rinc & (~io.rempty))
    rbin_reg := rbin_next
    
    val rptr_reg = RegInit(0.U(ptrWidth.W))
    val rptr_next = bin2gray(rbin_next)
    rptr_reg := rptr_next
    
    val raddr = rbin_reg(addrWidth - 1, 0)
    
    // < {wptr, waddr} generation
    
    // > wfull generation
    
    val rempty_reg = RegNext(emptyInGray(rptr_next, wptr_sync), init=false.B)
    
    // < wfull generation
    
    io.ptrs.rptr       := rptr_reg
    io.mem_rctrl.rclk  := clock
    io.mem_rctrl.raddr := raddr
    io.rempty          := rempty_reg
    
}

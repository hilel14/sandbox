/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.hilel14.swing.sample.application;

/**
 *
 * @author hilel
 */
public interface ProgressListener {

    public void showProgress(int percent);

    public void showMessage(String message);
}

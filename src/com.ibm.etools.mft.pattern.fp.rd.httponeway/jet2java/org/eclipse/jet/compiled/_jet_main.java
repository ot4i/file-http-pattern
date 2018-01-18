package org.eclipse.jet.compiled;

import org.eclipse.jet.JET2Context;
import org.eclipse.jet.JET2Template;
import org.eclipse.jet.JET2Writer;
import org.eclipse.jet.taglib.RuntimeTagElement;
import org.eclipse.jet.taglib.TagInfo;

public class _jet_main implements JET2Template {
    private static final String _jetns_c = "org.eclipse.jet.controlTags"; //$NON-NLS-1$
    private static final String _jetns_ws = "org.eclipse.jet.workspaceTags"; //$NON-NLS-1$

    public _jet_main() {
        super();
    }

    private static final String NL = System.getProperty("line.separator"); //$NON-NLS-1$
    
    private static final TagInfo _td_c_setVariable_2_1 = new TagInfo("c:setVariable", //$NON-NLS-1$
            2, 1,
            new String[] {
                "select", //$NON-NLS-1$
                "var", //$NON-NLS-1$
            },
            new String[] {
                "/*", //$NON-NLS-1$
                "root", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_choose_3_1 = new TagInfo("c:choose", //$NON-NLS-1$
            3, 1,
            new String[] {
            },
            new String[] {
            } );
    private static final TagInfo _td_c_when_4_5 = new TagInfo("c:when", //$NON-NLS-1$
            4, 5,
            new String[] {
                "test", //$NON-NLS-1$
            },
            new String[] {
                "namespace-uri($root) = 'com.ibm.etools.mft.pattern.fp.rd.httponeway.pattern'", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_include_5_9 = new TagInfo("c:include", //$NON-NLS-1$
            5, 9,
            new String[] {
                "template", //$NON-NLS-1$
            },
            new String[] {
                "pattern/templates/main.jet", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_otherwise_7_5 = new TagInfo("c:otherwise", //$NON-NLS-1$
            7, 5,
            new String[] {
            },
            new String[] {
            } );
    private static final TagInfo _td_c_log_8_9 = new TagInfo("c:log", //$NON-NLS-1$
            8, 9,
            new String[] {
                "severity", //$NON-NLS-1$
            },
            new String[] {
                "error", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_get_9_37 = new TagInfo("c:get", //$NON-NLS-1$
            9, 37,
            new String[] {
                "select", //$NON-NLS-1$
            },
            new String[] {
                "namespace-uri($root)", //$NON-NLS-1$
            } );
    private static final TagInfo _td_c_if_15_1 = new TagInfo("c:if", //$NON-NLS-1$
            15, 1,
            new String[] {
                "test", //$NON-NLS-1$
            },
            new String[] {
                "isVariableDefined('org.eclipse.jet.resource.project.name')", //$NON-NLS-1$
            } );
    private static final TagInfo _td_ws_file_16_5 = new TagInfo("ws:file", //$NON-NLS-1$
            16, 5,
            new String[] {
                "encoding", //$NON-NLS-1$
                "template", //$NON-NLS-1$
                "path", //$NON-NLS-1$
            },
            new String[] {
                "UTF-8", //$NON-NLS-1$
                "templates/dump.jet", //$NON-NLS-1$
                "{$org.eclipse.jet.resource.project.name}/dump.xml", //$NON-NLS-1$
            } );

    public void generate(final JET2Context context, final JET2Writer __out) {
        JET2Writer out = __out;
        RuntimeTagElement _jettag_c_setVariable_2_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "setVariable", "c:setVariable", _td_c_setVariable_2_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_setVariable_2_1.setRuntimeParent(null);
        _jettag_c_setVariable_2_1.setTagInfo(_td_c_setVariable_2_1);
        _jettag_c_setVariable_2_1.doStart(context, out);
        _jettag_c_setVariable_2_1.doEnd();
        RuntimeTagElement _jettag_c_choose_3_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "choose", "c:choose", _td_c_choose_3_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_choose_3_1.setRuntimeParent(null);
        _jettag_c_choose_3_1.setTagInfo(_td_c_choose_3_1);
        _jettag_c_choose_3_1.doStart(context, out);
        JET2Writer _jettag_c_choose_3_1_saved_out = out;
        while (_jettag_c_choose_3_1.okToProcessBody()) {
            out = out.newNestedContentWriter();
            RuntimeTagElement _jettag_c_when_4_5 = context.getTagFactory().createRuntimeTag(_jetns_c, "when", "c:when", _td_c_when_4_5); //$NON-NLS-1$ //$NON-NLS-2$
            _jettag_c_when_4_5.setRuntimeParent(_jettag_c_choose_3_1);
            _jettag_c_when_4_5.setTagInfo(_td_c_when_4_5);
            _jettag_c_when_4_5.doStart(context, out);
            JET2Writer _jettag_c_when_4_5_saved_out = out;
            while (_jettag_c_when_4_5.okToProcessBody()) {
                out = out.newNestedContentWriter();
                RuntimeTagElement _jettag_c_include_5_9 = context.getTagFactory().createRuntimeTag(_jetns_c, "include", "c:include", _td_c_include_5_9); //$NON-NLS-1$ //$NON-NLS-2$
                _jettag_c_include_5_9.setRuntimeParent(_jettag_c_when_4_5);
                _jettag_c_include_5_9.setTagInfo(_td_c_include_5_9);
                _jettag_c_include_5_9.doStart(context, out);
                _jettag_c_include_5_9.doEnd();
                _jettag_c_when_4_5.handleBodyContent(out);
            }
            out = _jettag_c_when_4_5_saved_out;
            _jettag_c_when_4_5.doEnd();
            RuntimeTagElement _jettag_c_otherwise_7_5 = context.getTagFactory().createRuntimeTag(_jetns_c, "otherwise", "c:otherwise", _td_c_otherwise_7_5); //$NON-NLS-1$ //$NON-NLS-2$
            _jettag_c_otherwise_7_5.setRuntimeParent(_jettag_c_choose_3_1);
            _jettag_c_otherwise_7_5.setTagInfo(_td_c_otherwise_7_5);
            _jettag_c_otherwise_7_5.doStart(context, out);
            JET2Writer _jettag_c_otherwise_7_5_saved_out = out;
            while (_jettag_c_otherwise_7_5.okToProcessBody()) {
                out = out.newNestedContentWriter();
                RuntimeTagElement _jettag_c_log_8_9 = context.getTagFactory().createRuntimeTag(_jetns_c, "log", "c:log", _td_c_log_8_9); //$NON-NLS-1$ //$NON-NLS-2$
                _jettag_c_log_8_9.setRuntimeParent(_jettag_c_otherwise_7_5);
                _jettag_c_log_8_9.setTagInfo(_td_c_log_8_9);
                _jettag_c_log_8_9.doStart(context, out);
                JET2Writer _jettag_c_log_8_9_saved_out = out;
                while (_jettag_c_log_8_9.okToProcessBody()) {
                    out = out.newNestedContentWriter();
                    out.write("            Unrecognised transform [");  //$NON-NLS-1$        
                    RuntimeTagElement _jettag_c_get_9_37 = context.getTagFactory().createRuntimeTag(_jetns_c, "get", "c:get", _td_c_get_9_37); //$NON-NLS-1$ //$NON-NLS-2$
                    _jettag_c_get_9_37.setRuntimeParent(_jettag_c_log_8_9);
                    _jettag_c_get_9_37.setTagInfo(_td_c_get_9_37);
                    _jettag_c_get_9_37.doStart(context, out);
                    _jettag_c_get_9_37.doEnd();
                    out.write("]");  //$NON-NLS-1$        
                    out.write(NL);         
                    _jettag_c_log_8_9.handleBodyContent(out);
                }
                out = _jettag_c_log_8_9_saved_out;
                _jettag_c_log_8_9.doEnd();
                _jettag_c_otherwise_7_5.handleBodyContent(out);
            }
            out = _jettag_c_otherwise_7_5_saved_out;
            _jettag_c_otherwise_7_5.doEnd();
            _jettag_c_choose_3_1.handleBodyContent(out);
        }
        out = _jettag_c_choose_3_1_saved_out;
        _jettag_c_choose_3_1.doEnd();
        out.write(NL);         
        // In debug mode dump the input model 
        RuntimeTagElement _jettag_c_if_15_1 = context.getTagFactory().createRuntimeTag(_jetns_c, "if", "c:if", _td_c_if_15_1); //$NON-NLS-1$ //$NON-NLS-2$
        _jettag_c_if_15_1.setRuntimeParent(null);
        _jettag_c_if_15_1.setTagInfo(_td_c_if_15_1);
        _jettag_c_if_15_1.doStart(context, out);
        while (_jettag_c_if_15_1.okToProcessBody()) {
            out.write("    ");  //$NON-NLS-1$        
            RuntimeTagElement _jettag_ws_file_16_5 = context.getTagFactory().createRuntimeTag(_jetns_ws, "file", "ws:file", _td_ws_file_16_5); //$NON-NLS-1$ //$NON-NLS-2$
            _jettag_ws_file_16_5.setRuntimeParent(_jettag_c_if_15_1);
            _jettag_ws_file_16_5.setTagInfo(_td_ws_file_16_5);
            _jettag_ws_file_16_5.doStart(context, out);
            _jettag_ws_file_16_5.doEnd();
            out.write(NL);         
            _jettag_c_if_15_1.handleBodyContent(out);
        }
        _jettag_c_if_15_1.doEnd();
        out.write(NL);         
    }
}

package com.trivadis.plsql.formatter.settings.tests.grammar.plsql;

import com.trivadis.plsql.formatter.settings.ConfiguredTestFormatter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class Inline_pragma extends ConfiguredTestFormatter {

    @Test
    public void example_13_4() throws IOException {
        var input = """
                create procedure p1(x pls_integer) is
                    pragma
                    inline
                    (
                    p1
                    ,
                    'YES'
                    )
                    ;
                    pragma inline(p1,'NO');
                    pragma inline ( p1 , 'YES' ) ;
                 begin
                    x := p1(1) + p1(2) + 17;    -- These 2 invocations to p1 are not inlined
                 end;
                 /
                """;
        var actual = formatter.format(input);
        var expected = """
                create procedure p1(x pls_integer) is
                   pragma inline (p1, 'YES');
                   pragma inline(p1, 'NO');
                   pragma inline (p1, 'YES');
                begin
                   x := p1(1) + p1(2) + 17;    -- These 2 invocations to p1 are not inlined
                end;
                /
                """;
        assertEquals(expected, actual);
    }
}

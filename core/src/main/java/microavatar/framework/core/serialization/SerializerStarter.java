package microavatar.framework.core.serialization;

import microavatar.framework.core.serialization.impl.JsonProtobuf2Serializer;
import org.springframework.context.annotation.Import;

@Import({JsonProtobuf2Serializer.class})
public class SerializerStarter {
}
